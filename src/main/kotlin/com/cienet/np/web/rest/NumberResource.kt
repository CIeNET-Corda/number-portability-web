package com.cienet.np.web.rest

import com.cienet.npdapp.number.NumberAccessFlow
import com.cienet.npdapp.number.NumberState
import com.cienet.npdapp.number.NumberTransferToFlow
import com.cienet.np.corda.NodeRPCConnection
import com.cienet.np.corda.RpcInfo
import com.cienet.np.service.dto.NumberDTO
import com.codahale.metrics.annotation.Timed
import net.corda.core.messaging.CordaRPCOps
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import io.github.jhipster.web.util.ResponseUtil

@RestController
@RequestMapping("/api")
class NumberResource(private val nodeRpcConnection: NodeRPCConnection) {
    private val log = LoggerFactory.getLogger(NumberResource::class.java)

    private val rpcInfoMap = mapOf(
        "operator1" to RpcInfo("127.0.0.1", 19800, "test", "test"),
        "operator2" to RpcInfo("127.0.0.1", 19801, "test", "test"),
        "operator3" to RpcInfo("127.0.0.1", 19802, "test", "test"))

    @GetMapping("/number/{operator}")
    @Timed
    fun getAllNumbers(@PathVariable operator: String): ResponseEntity<List<NumberDTO>> {
        log.info("Get Number resources for operator: $operator")
        rpcInfoMap.get(operator)?.let {
            // val proxy: CordaRPCOps = nodeRpcConnection.getRpcConnection(it).proxy

            // proxy.vaultQuery(NumberState::class.java).states.map { NumberDTO.fromModel(operator, it.state.data.number,  1)}
            return ResponseEntity(listOf(NumberDTO.fromModel(operator, "13511112222", 1)), HttpStatus.OK)
        }

        // return ResponseUtil.wrapOrNotFound()
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PostMapping("/number")
    @Timed
    fun publishNumber(@RequestParam("operator") operator: String, @RequestParam("number") number: String) {
        log.info("Issue a number: $number to operator: $operator.")

    }

    @PostMapping("/number/porting")
    @Timed
    fun portingNumber(@RequestParam("number") number: String,
                       @RequestParam("operatorFrom") operatorFrom: String,
                       @RequestParam("operatorTo") operatorTo: String) {
        log.info("Porting a number: $number from $operatorFrom to $operatorTo.")

    }
}
