package kr.co.kcd.example

import com.google.auto.service.AutoService
import reactor.blockhound.BlockHound
import reactor.blockhound.integration.BlockHoundIntegration

@Suppress("unused")
@AutoService(BlockHoundIntegration::class)
class LoggingBlockHoundIntegration : BlockHoundIntegration {
    override fun applyTo(builder: BlockHound.Builder) {
        // if you want print cause of blocking method, uncomment this line
        //builder.blockingMethodCallback { Exception(it.toString()).printStackTrace() }
    }
}