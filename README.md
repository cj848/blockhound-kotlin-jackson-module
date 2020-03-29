if run gradlew test error occurs like this

```
reactor.blockhound.BlockingOperationError: Blocking call! jdk.internal.misc.Unsafe#park
	at jdk.internal.misc.Unsafe.park(Unsafe.java) ~[?:?]
	Suppressed: reactor.core.publisher.FluxOnAssembly$OnAssemblyException: 
Error has been observed at the following site(s):
	|_ checkpoint ⇢ Request to POST http://127.0.0.1:53907/slack [DefaultWebClient]
Stack trace:
		at jdk.internal.misc.Unsafe.park(Unsafe.java) ~[?:?]
		at java.util.concurrent.locks.LockSupport.park(LockSupport.java:194) ~[?:?]
		at java.util.concurrent.locks.AbstractQueuedSynchronizer.parkAndCheckInterrupt(AbstractQueuedSynchronizer.java:885) ~[?:?]
		at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireQueued(AbstractQueuedSynchronizer.java:917) ~[?:?]
		at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquire(AbstractQueuedSynchronizer.java:1240) ~[?:?]
		at java.util.concurrent.locks.ReentrantLock.lock(ReentrantLock.java:267) ~[?:?]
		at org.gradle.internal.remote.internal.hub.MessageHub$ChannelDispatch.dispatch(MessageHub.java:368) ~[?:?]
		at org.gradle.internal.dispatch.ProxyDispatchAdapter$DispatchingInvocationHandler.invoke(ProxyDispatchAdapter.java:94) ~[?:?]
		at com.sun.proxy.$Proxy3.output(Unknown Source) ~[?:?]
		at org.gradle.api.internal.tasks.testing.processors.TestOutputRedirector$Forwarder.onOutput(TestOutputRedirector.java:75) ~[?:?]
		at org.gradle.api.internal.tasks.testing.processors.DefaultStandardOutputRedirector$WriteAction.text(DefaultStandardOutputRedirector.java:89) ~[?:?]
		at org.gradle.internal.io.LineBufferingOutputStream.flush(LineBufferingOutputStream.java:98) ~[?:?]
		at org.gradle.internal.io.LineBufferingOutputStream.write(LineBufferingOutputStream.java:82) ~[?:?]
		at java.io.OutputStream.write(OutputStream.java:157) ~[?:?]
		at java.io.PrintStream.write(PrintStream.java:559) ~[?:?]
		at org.gradle.internal.io.LinePerThreadBufferingOutputStream.write(LinePerThreadBufferingOutputStream.java:213) ~[?:?]
		at org.apache.logging.log4j.core.appender.ConsoleAppender$SystemOutStream.write(ConsoleAppender.java:339) ~[log4j-core-2.12.1.jar:2.12.1]
		at java.io.PrintStream.write(PrintStream.java:559) ~[?:?]
		at org.apache.logging.log4j.core.util.CloseShieldOutputStream.write(CloseShieldOutputStream.java:53) ~[log4j-core-2.12.1.jar:2.12.1]
		at org.apache.logging.log4j.core.appender.OutputStreamManager.writeToDestination(OutputStreamManager.java:261) ~[log4j-core-2.12.1.jar:2.12.1]
		at org.apache.logging.log4j.core.appender.OutputStreamManager.flushBuffer(OutputStreamManager.java:293) ~[log4j-core-2.12.1.jar:2.12.1]
		at org.apache.logging.log4j.core.appender.OutputStreamManager.drain(OutputStreamManager.java:350) ~[log4j-core-2.12.1.jar:2.12.1]
		at org.apache.logging.log4j.core.layout.TextEncoderHelper.drainIfByteBufferFull(TextEncoderHelper.java:260) ~[log4j-core-2.12.1.jar:2.12.1]
		at org.apache.logging.log4j.core.layout.TextEncoderHelper.writeAndEncodeAsMuchAsPossible(TextEncoderHelper.java:199) ~[log4j-core-2.12.1.jar:2.12.1]
		at org.apache.logging.log4j.core.layout.TextEncoderHelper.encodeChunkedText(TextEncoderHelper.java:147) ~[log4j-core-2.12.1.jar:2.12.1]
		at org.apache.logging.log4j.core.layout.TextEncoderHelper.encodeText(TextEncoderHelper.java:58) ~[log4j-core-2.12.1.jar:2.12.1]
		at org.apache.logging.log4j.core.layout.StringBuilderEncoder.encode(StringBuilderEncoder.java:68) ~[log4j-core-2.12.1.jar:2.12.1]
		at org.apache.logging.log4j.core.layout.StringBuilderEncoder.encode(StringBuilderEncoder.java:32) ~[log4j-core-2.12.1.jar:2.12.1]
		at org.apache.logging.log4j.core.layout.PatternLayout.encode(PatternLayout.java:227) ~[log4j-core-2.12.1.jar:2.12.1]
		at org.apache.logging.log4j.core.layout.PatternLayout.encode(PatternLayout.java:59) ~[log4j-core-2.12.1.jar:2.12.1]
		at org.apache.logging.log4j.core.appender.AbstractOutputStreamAppender.directEncodeEvent(AbstractOutputStreamAppender.java:197) ~[log4j-core-2.12.1.jar:2.12.1]
		at org.apache.logging.log4j.core.appender.AbstractOutputStreamAppender.tryAppend(AbstractOutputStreamAppender.java:190) ~[log4j-core-2.12.1.jar:2.12.1]
		at org.apache.logging.log4j.core.appender.AbstractOutputStreamAppender.append(AbstractOutputStreamAppender.java:181) ~[log4j-core-2.12.1.jar:2.12.1]
		at org.apache.logging.log4j.core.config.AppenderControl.tryCallAppender(AppenderControl.java:156) ~[log4j-core-2.12.1.jar:2.12.1]
		at org.apache.logging.log4j.core.config.AppenderControl.callAppender0(AppenderControl.java:129) ~[log4j-core-2.12.1.jar:2.12.1]
		at org.apache.logging.log4j.core.config.AppenderControl.callAppenderPreventRecursion(AppenderControl.java:120) ~[log4j-core-2.12.1.jar:2.12.1]
		at org.apache.logging.log4j.core.config.AppenderControl.callAppender(AppenderControl.java:84) ~[log4j-core-2.12.1.jar:2.12.1]
		at org.apache.logging.log4j.core.config.LoggerConfig.callAppenders(LoggerConfig.java:543) ~[log4j-core-2.12.1.jar:2.12.1]
		at org.apache.logging.log4j.core.config.LoggerConfig.processLogEvent(LoggerConfig.java:502) ~[log4j-core-2.12.1.jar:2.12.1]
		at org.apache.logging.log4j.core.config.LoggerConfig.log(LoggerConfig.java:485) ~[log4j-core-2.12.1.jar:2.12.1]
		at org.apache.logging.log4j.core.config.LoggerConfig.log(LoggerConfig.java:460) ~[log4j-core-2.12.1.jar:2.12.1]
		at org.apache.logging.log4j.core.config.AwaitCompletionReliabilityStrategy.log(AwaitCompletionReliabilityStrategy.java:82) ~[log4j-core-2.12.1.jar:2.12.1]
		at org.apache.logging.log4j.core.Logger.log(Logger.java:162) ~[log4j-core-2.12.1.jar:2.12.1]
		at org.apache.logging.log4j.spi.AbstractLogger.tryLogMessage(AbstractLogger.java:2190) ~[log4j-api-2.12.1.jar:2.12.1]
		at org.apache.logging.log4j.spi.AbstractLogger.logMessageTrackRecursion(AbstractLogger.java:2144) ~[log4j-api-2.12.1.jar:2.12.1]
		at org.apache.logging.log4j.spi.AbstractLogger.logMessageSafely(AbstractLogger.java:2127) ~[log4j-api-2.12.1.jar:2.12.1]
		at org.apache.logging.log4j.spi.AbstractLogger.logMessage(AbstractLogger.java:2003) ~[log4j-api-2.12.1.jar:2.12.1]
		at org.apache.logging.log4j.spi.AbstractLogger.logIfEnabled(AbstractLogger.java:1975) ~[log4j-api-2.12.1.jar:2.12.1]
		at org.apache.logging.slf4j.Log4jLogger.warn(Log4jLogger.java:259) ~[log4j-slf4j-impl-2.12.1.jar:2.12.1]
		at reactor.util.Loggers$Slf4JLogger.warn(Loggers.java:299) ~[reactor-core-3.3.4.RELEASE.jar:3.3.4.RELEASE]
		at reactor.netty.http.client.HttpClientConnect$HttpObserver.onUncaughtException(HttpClientConnect.java:394) ~[reactor-netty-0.9.6.RELEASE.jar:0.9.6.RELEASE]
		at reactor.netty.ReactorNetty$CompositeConnectionObserver.onUncaughtException(ReactorNetty.java:507) ~[reactor-netty-0.9.6.RELEASE.jar:0.9.6.RELEASE]
		at reactor.netty.resources.PooledConnectionProvider$DisposableAcquire.onUncaughtException(PooledConnectionProvider.java:515) ~[reactor-netty-0.9.6.RELEASE.jar:0.9.6.RELEASE]
		at reactor.netty.resources.PooledConnectionProvider$PooledConnection.onUncaughtException(PooledConnectionProvider.java:366) ~[reactor-netty-0.9.6.RELEASE.jar:0.9.6.RELEASE]
		at reactor.netty.http.client.HttpClientOperations.onOutboundError(HttpClientOperations.java:509) ~[reactor-netty-0.9.6.RELEASE.jar:0.9.6.RELEASE]
		at reactor.netty.channel.ChannelOperations.onError(ChannelOperations.java:213) ~[reactor-netty-0.9.6.RELEASE.jar:0.9.6.RELEASE]
		at reactor.core.publisher.Operators.error(Operators.java:185) ~[reactor-core-3.3.4.RELEASE.jar:3.3.4.RELEASE]
		at reactor.core.publisher.MonoError.subscribe(MonoError.java:52) ~[reactor-core-3.3.4.RELEASE.jar:3.3.4.RELEASE]
		at reactor.core.publisher.MonoDefer.subscribe(MonoDefer.java:52) ~[reactor-core-3.3.4.RELEASE.jar:3.3.4.RELEASE]
		at reactor.netty.http.client.HttpClientConnect$HttpIOHandlerObserver.onStateChange(HttpClientConnect.java:435) ~[reactor-netty-0.9.6.RELEASE.jar:0.9.6.RELEASE]
		at reactor.netty.ReactorNetty$CompositeConnectionObserver.onStateChange(ReactorNetty.java:514) ~[reactor-netty-0.9.6.RELEASE.jar:0.9.6.RELEASE]
		at reactor.netty.resources.PooledConnectionProvider$DisposableAcquire.onStateChange(PooledConnectionProvider.java:523) ~[reactor-netty-0.9.6.RELEASE.jar:0.9.6.RELEASE]
		at reactor.netty.resources.PooledConnectionProvider$PooledConnection.onStateChange(PooledConnectionProvider.java:429) ~[reactor-netty-0.9.6.RELEASE.jar:0.9.6.RELEASE]
		at reactor.netty.channel.ChannelOperationsHandler.channelActive(ChannelOperationsHandler.java:60) ~[reactor-netty-0.9.6.RELEASE.jar:0.9.6.RELEASE]
		at io.netty.channel.AbstractChannelHandlerContext.invokeChannelActive(AbstractChannelHandlerContext.java:230) [netty-transport-4.1.48.Final.jar:4.1.48.Final]
		at io.netty.channel.AbstractChannelHandlerContext.invokeChannelActive(AbstractChannelHandlerContext.java:216) [netty-transport-4.1.48.Final.jar:4.1.48.Final]
		at io.netty.channel.AbstractChannelHandlerContext.fireChannelActive(AbstractChannelHandlerContext.java:209) [netty-transport-4.1.48.Final.jar:4.1.48.Final]
		at io.netty.channel.CombinedChannelDuplexHandler$DelegatingChannelHandlerContext.fireChannelActive(CombinedChannelDuplexHandler.java:412) [netty-transport-4.1.48.Final.jar:4.1.48.Final]
		at io.netty.channel.ChannelInboundHandlerAdapter.channelActive(ChannelInboundHandlerAdapter.java:69) [netty-transport-4.1.48.Final.jar:4.1.48.Final]
		at io.netty.channel.CombinedChannelDuplexHandler.channelActive(CombinedChannelDuplexHandler.java:211) [netty-transport-4.1.48.Final.jar:4.1.48.Final]
		at io.netty.channel.AbstractChannelHandlerContext.invokeChannelActive(AbstractChannelHandlerContext.java:230) [netty-transport-4.1.48.Final.jar:4.1.48.Final]
		at io.netty.channel.AbstractChannelHandlerContext.invokeChannelActive(AbstractChannelHandlerContext.java:216) [netty-transport-4.1.48.Final.jar:4.1.48.Final]
		at io.netty.channel.AbstractChannelHandlerContext.fireChannelActive(AbstractChannelHandlerContext.java:209) [netty-transport-4.1.48.Final.jar:4.1.48.Final]
		at io.netty.channel.DefaultChannelPipeline$HeadContext.channelActive(DefaultChannelPipeline.java:1398) [netty-transport-4.1.48.Final.jar:4.1.48.Final]
		at io.netty.channel.AbstractChannelHandlerContext.invokeChannelActive(AbstractChannelHandlerContext.java:230) [netty-transport-4.1.48.Final.jar:4.1.48.Final]
		at io.netty.channel.AbstractChannelHandlerContext.invokeChannelActive(AbstractChannelHandlerContext.java:216) [netty-transport-4.1.48.Final.jar:4.1.48.Final]
		at io.netty.channel.DefaultChannelPipeline.fireChannelActive(DefaultChannelPipeline.java:895) [netty-transport-4.1.48.Final.jar:4.1.48.Final]
		at io.netty.channel.nio.AbstractNioChannel$AbstractNioUnsafe.fulfillConnectPromise(AbstractNioChannel.java:305) [netty-transport-4.1.48.Final.jar:4.1.48.Final]
		at io.netty.channel.nio.AbstractNioChannel$AbstractNioUnsafe.finishConnect(AbstractNioChannel.java:335) [netty-transport-4.1.48.Final.jar:4.1.48.Final]
		at io.netty.channel.nio.NioEventLoop.processSelectedKey(NioEventLoop.java:702) [netty-transport-4.1.48.Final.jar:4.1.48.Final]
		at io.netty.channel.nio.NioEventLoop.processSelectedKeysOptimized(NioEventLoop.java:650) [netty-transport-4.1.48.Final.jar:4.1.48.Final]
		at io.netty.channel.nio.NioEventLoop.processSelectedKeys(NioEventLoop.java:576) [netty-transport-4.1.48.Final.jar:4.1.48.Final]
		at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:493) [netty-transport-4.1.48.Final.jar:4.1.48.Final]
		at io.netty.util.concurrent.SingleThreadEventExecutor$4.run(SingleThreadEventExecutor.java:989) [netty-common-4.1.48.Final.jar:4.1.48.Final]
		at io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74) [netty-common-4.1.48.Final.jar:4.1.48.Final]
		at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30) [netty-common-4.1.48.Final.jar:4.1.48.Final]
		at java.lang.Thread.run(Thread.java:830) [?:?]
```

but, if you uncomment in build.gradle.kts below line. tests are succesful.

implementation("com.fasterxml.jackson.module:jackson-module-kotlin") 