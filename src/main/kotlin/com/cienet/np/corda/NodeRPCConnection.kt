package com.cienet.np.corda

import net.corda.client.rpc.CordaRPCClient
import net.corda.client.rpc.CordaRPCConnection
import net.corda.core.utilities.NetworkHostAndPort
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

data class RpcInfo(val host: String, val port: Int, val userName: String, val password: String)

@Component
class NodeRPCConnection {
    companion object {
        private val log = LoggerFactory.getLogger(NodeRPCConnection::class.java)
    }

    val rpcConnections = mutableMapOf<RpcInfo, CordaRPCConnection>()

    fun getRpcConnection(rpcInfo: RpcInfo): CordaRPCConnection =
        rpcConnections.getOrElse(rpcInfo) {
            log.info("RPC connection to ${rpcInfo.host}:${rpcInfo.port}, using name:${rpcInfo.userName}, password:${rpcInfo.password}")
            val rpcAddress = NetworkHostAndPort(rpcInfo.host, rpcInfo.port)
            val rpcClient = CordaRPCClient(rpcAddress)
            val rpcConnection = rpcClient.start(rpcInfo.userName, rpcInfo.password)
            rpcConnections[rpcInfo] = rpcConnection
            return rpcConnection
        }
}
