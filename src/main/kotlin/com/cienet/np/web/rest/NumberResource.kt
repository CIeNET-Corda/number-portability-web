package com.cienet.np.web.rest

import com.cienet.np.corda.NodeRPCConnection
import com.cienet.np.corda.RpcInfo
import com.cienet.np.service.dto.NumberDTO
import com.codahale.metrics.annotation.Timed
import net.corda.core.messaging.CordaRPCOps
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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

            return ResponseEntity(listOf(NumberDTO.fromModel("Operator1", "13511112222", 1)), HttpStatus.OK)
        }

        return ResponseEntity(HttpStatus.OK)
    }

    @PostMapping("/number")
    @Timed
    fun issueNumber() {

    }

    @PostMapping("/number/transfer")
    @Timed
    fun transferNumber() {

    }
}
