package com.cienet.np.web.rest

import com.cienet.npdapp.number.NumberAccessFlow
import com.cienet.npdapp.number.NumberState
import com.cienet.np.corda.NodeRPCConnection
import com.cienet.np.corda.RpcInfo
import com.cienet.np.service.dto.NumberDTO
import com.cienet.npdapp.number.NumberTransferFromFlow
import com.codahale.metrics.annotation.Timed
import net.corda.core.messaging.CordaRPCOps
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import net.corda.core.contracts.StateAndRef
import net.corda.core.flows.FlowException
import net.corda.core.identity.CordaX500Name
import net.corda.core.messaging.startTrackedFlow
import net.corda.core.node.services.Vault
import net.corda.core.node.services.vault.QueryCriteria
import net.corda.core.utilities.getOrThrow
import org.springframework.beans.factory.annotation.Value

@RestController
@RequestMapping("/api")
class NumberResource(private val nodeRpcConnection: NodeRPCConnection,
                     @param:Value("\${cordapp.server}") private val server: String) {
    private val log = LoggerFactory.getLogger(NumberResource::class.java)

    private val rpcInfoMap = mapOf(
        "operator1" to RpcInfo(server, 10006, "user1", "test"),
        "operator2" to RpcInfo(server, 10009, "user1", "test"),
        "operator3" to RpcInfo(server, 10012, "user1", "test"))

    @GetMapping("/number/{operator}")
    @Timed
    fun getAllNumbers(@PathVariable operator: String): ResponseEntity<List<NumberDTO>> {
        log.info("Get Number resources for operator: $operator")
        var numberList = listOf<NumberDTO>()
        rpcInfoMap[operator]?.let {
            val proxy: CordaRPCOps = nodeRpcConnection.getRpcConnection(it).proxy
            numberList = queryAllState(proxy).map { stateRef -> NumberDTO.fromModel(operator, stateRef.state.data.number, 1)}.toList()
        }

        return ResponseEntity(numberList, HttpStatus.OK)
    }

    @PostMapping("/number")
    @Timed
    fun publishNumber(@RequestParam("operator") operator: String, @RequestParam("number") number: String) : NumberDTO {
        log.info("Issue a number: $number to operator: $operator.")
        rpcInfoMap[operator]?.let {
            val proxy: CordaRPCOps = nodeRpcConnection.getRpcConnection(it).proxy
            val ret = performAccessFlow(number, proxy)
            return NumberDTO.fromModel(operator, ret.state.data.number,  1)
        }

        return NumberDTO.fromModel(operator, "", 1)
    }

    @PostMapping("/number/porting")
    @Timed
    fun portingNumber(@RequestParam("number") number: String,
                       @RequestParam("operatorFrom") operatorFrom: String,
                       @RequestParam("operatorTo") operatorTo: String): NumberDTO {
        log.info("Porting a number: $number from $operatorFrom to $operatorTo.")

        val fromOperatorName: CordaX500Name? = rpcInfoMap[operatorFrom]?.let {
            nodeRpcConnection.getRpcConnection(it).proxy.nodeInfo().legalIdentities.first().nameOrNull()
        }

        rpcInfoMap[operatorTo]?.let {
            val proxy: CordaRPCOps = nodeRpcConnection.getRpcConnection(it).proxy
            val ret = performTransferFromFlow(number, fromOperatorName!!, proxy)
            return NumberDTO.fromModel(operatorTo, ret.state.data.number,  1)
        }

        return NumberDTO.fromModel(operatorTo, "", 1)
    }

    fun queryAllState(cordaRPCOps: CordaRPCOps): List<StateAndRef<NumberState>> {
        val criteria = QueryCriteria.VaultQueryCriteria(Vault.StateStatus.UNCONSUMED)
        val results = cordaRPCOps.vaultQueryByCriteria(criteria, NumberState::class.java)
        return results.states.filter{
                it.state.data.currOperator == cordaRPCOps.nodeInfo().legalIdentities.first()
            }.toList()
    }

    fun queryStateBy(number: String, cordaRPCOps: CordaRPCOps): StateAndRef<NumberState> {
        val criteria = QueryCriteria.VaultQueryCriteria(Vault.StateStatus.UNCONSUMED)
        val results = cordaRPCOps.vaultQueryByCriteria(criteria, NumberState::class.java)
        return results.states.stream()
            .filter{it.state.data.number == number && it.state.data.currOperator == cordaRPCOps.nodeInfo().legalIdentities.first()}
            .findAny()
            .orElse(null) ?: throw FlowException("Can not find this number belongs to the this party.")
    }

    fun performAccessFlow(number: String, cordaRPCOps: CordaRPCOps): StateAndRef<NumberState> {
        cordaRPCOps.startTrackedFlow(NumberAccessFlow::Initiator, number).returnValue.getOrThrow()
        return queryStateBy(number, cordaRPCOps)
    }

    fun performTransferFromFlow(number: String, partyName: CordaX500Name, cordaRPCOps: CordaRPCOps): StateAndRef<NumberState> {
        val otherParty = cordaRPCOps.wellKnownPartyFromX500Name(partyName) ?: throw FlowException("Can not find this party.")
        cordaRPCOps.startTrackedFlow(NumberTransferFromFlow::Initiator, number, otherParty).returnValue.getOrThrow()
        return queryStateBy(number, cordaRPCOps)
    }
}
