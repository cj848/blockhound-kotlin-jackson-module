package kr.co.kcd.example

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.core.*
import org.apache.logging.log4j.core.appender.AbstractAppender
import org.apache.logging.log4j.core.config.Property
import org.apache.logging.log4j.core.config.plugins.Plugin
import org.apache.logging.log4j.core.config.plugins.PluginAttribute
import org.apache.logging.log4j.core.config.plugins.PluginElement
import org.apache.logging.log4j.core.config.plugins.PluginFactory
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required
import org.apache.logging.log4j.core.layout.PatternLayout
import org.springframework.core.env.AbstractEnvironment
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.scheduler.Schedulers
import reactor.netty.http.client.HttpClient
import reactor.netty.tcp.TcpClient
import reactor.netty.tcp.TcpResources
import java.io.Serializable
import java.util.concurrent.TimeUnit

data class Attachment(
        val fallback: String,
        val text: String,
        val color: String
)

data class SlackMessage(
        val channel: String,
        val text: String,
        val attachments: List<Attachment> = listOf(),
        val icon_emoji: String,
        val username: String
)

@Plugin(name = "Slack", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE, printObject = true)
class SlackAppender(
        name: String,
        filter: Filter?,
        layout: Layout<out Serializable?>?,
        ignoreExceptions: Boolean,
        properties: Array<Property>,
        private val slackWebClient: WebClient,
        private val url: String,
        private val channel: String,
        private val username: String
) : AbstractAppender(name, filter, layout, ignoreExceptions, properties) {

    companion object {
        internal val ICON_MAP: Map<Int, String> = mapOf(
                Level.TRACE.intLevel() to ":pawprints:",
                Level.DEBUG.intLevel() to ":beetle:",
                Level.INFO.intLevel() to ":suspect:",
                Level.WARN.intLevel() to ":goberserk:",
                Level.ERROR.intLevel() to ":feelsgood:",
                Level.FATAL.intLevel() to ":finnadie:"
        )

        internal val COLOR_MAP: Map<Int, String> = mapOf(
                Level.TRACE.intLevel() to "#6f6d6d",
                Level.DEBUG.intLevel() to "#b5dae9",
                Level.INFO.intLevel() to "#5f9ea0",
                Level.WARN.intLevel() to "#ff9122",
                Level.ERROR.intLevel() to "#ff4444",
                Level.FATAL.intLevel() to "#b03e3c"
        )

        @JvmStatic
        @PluginFactory
        fun createAppender(
                @PluginAttribute("name") @Required(message = "No name provided") name: String,
                @PluginElement("Layout") layout: Layout<out Serializable?>?,
                @PluginElement("Filter") filter: Filter?,
                @PluginAttribute("url") @Required(message = "No webhookUrl provided") url: String,
                @PluginAttribute("channel") @Required(message = "No channel provided") channel: String,
                @PluginAttribute("username") @Required(message = "No username provided") username: String,
                @PluginAttribute("connectionTimeoutInMillis", defaultInt = 2000) connectionTimeoutInMillis: Int,
                @PluginAttribute("readTimeoutInSecond", defaultLong = 3) readTimeoutInSecond: Long,
                @PluginAttribute("writeTimeoutInSecond", defaultLong = 3) writeTimeoutInSecond: Long
        ): SlackAppender {
            return SlackAppender(
                    name = name,
                    filter = filter,
                    layout = layout ?: PatternLayout.createDefaultLayout(),
                    ignoreExceptions = false,
                    properties = Property.EMPTY_ARRAY,
                    slackWebClient = webClient(
                            connectionTimeoutInMillis = connectionTimeoutInMillis,
                            readTimeoutInSecond = readTimeoutInSecond,
                            writeTimeoutInSecond = writeTimeoutInSecond
                    ),
                    url = url,
                    channel = channel,
                    username = username
            )
        }
    }

    override fun append(event: LogEvent) {
        val profile = System.getProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME)
        event.message?.let {
            val slackMessage = SlackMessage(
                    channel = channel,
                    icon_emoji = ICON_MAP.getValue(event.level.intLevel()),
                    text = "[$profile] ${event.loggerName}",
                    attachments = listOf(
                            Attachment(
                                    color = COLOR_MAP.getValue(event.level.intLevel()),
                                    fallback = it.formattedMessage,
                                    text = layout.toSerializable(event).toString()
                            )
                    ),
                    username = username
            )

            slackWebClient
                    .post()
                    .uri(url)
                    .bodyValue(slackMessage)
                    .exchange()
                    .subscribeOn(Schedulers.elastic())
                    .subscribe()
        }
    }
}


fun webClient(
        connectionTimeoutInMillis: Int,
        readTimeoutInSecond: Long,
        writeTimeoutInSecond: Long
): WebClient {
    val tcpClient: TcpClient = TcpClient.create()
            .secure()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeoutInMillis)
            .doOnConnected {
                it.addHandlerLast(
                        ReadTimeoutHandler(
                                readTimeoutInSecond,
                                TimeUnit.SECONDS
                        )
                )
                        .addHandlerLast(
                                WriteTimeoutHandler(
                                        writeTimeoutInSecond,
                                        TimeUnit.SECONDS
                                )
                        )
            }

    Runtime.getRuntime().addShutdownHook(object : Thread() {
        override fun run() {
            TcpResources.disposeLoopsAndConnectionsLater().block()
        }
    })
    return WebClient.builder()
            .clientConnector(
                    ReactorClientHttpConnector(
                            HttpClient.from(tcpClient)
                    )
            )
            .defaultHeader(
                    HttpHeaders.CONTENT_TYPE,
                    MediaType.APPLICATION_JSON_VALUE
            )
            .build()
}
