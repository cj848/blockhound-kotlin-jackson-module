package kr.co.kcd.example

import org.apache.logging.log4j.Level
import org.apache.logging.log4j.core.impl.DefaultLogEventFactory
import org.apache.logging.log4j.message.ObjectMessage
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertTimeout
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.AbstractEnvironment
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import java.time.Duration

private const val channel = "#channel"
private const val userName = "userName"
private const val testLogger = "testLogger"
private const val slackMessage = "success"
private const val profile = "testttt"
private val slackSuccessRequest = SlackMessage(
        channel = channel,
        icon_emoji = SlackAppender.ICON_MAP.getValue(Level.INFO.intLevel()),
        text = "[$profile] $testLogger",
        attachments = listOf(
                Attachment(
                        color = SlackAppender.COLOR_MAP.getValue(Level.INFO.intLevel()),
                        fallback = slackMessage,
                        text = slackMessage
                )
        ),
        username = userName
)

private val slackSuccessResponse = mapOf("test" to "success")
private val slackErrorResponse = mapOf("test" to "error")
private var slackSuccessCount = 0
private var slackFailCount = 0

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class SlackAppenderTest {
    @LocalServerPort
    var port: Int = 0

    lateinit var sut: SlackAppender

    val logFactory = DefaultLogEventFactory()

    @BeforeEach
    fun before() {
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, profile)
        sut = SlackAppender.createAppender(
                name = "test",
                connectionTimeoutInMillis = 1000,
                filter = null,
                layout = null,
                readTimeoutInSecond = 1000L,
                url = "http://127.0.0.1:$port/slack",
                writeTimeoutInSecond = 1000L,
                username = userName,
                channel = channel
        )
        slackSuccessCount = 0
        slackFailCount = 0
    }

    @Test
    fun `normal logging case`() {
        assertThat(slackSuccessCount).isZero()

        sut.append(
                logFactory.createEvent(
                        testLogger,
                        null,
                        "test",
                        Level.INFO,
                        ObjectMessage(slackMessage),
                        listOf(),
                        null
                )
        )
        assertTimeout(Duration.ofSeconds(100)) {
            while (slackSuccessCount == 0) {
                Thread.yield()
            }
        }
    }

    @Test
    fun `error logging test`() {
        assertThat(slackFailCount).isZero()

        sut.append(
                logFactory.createEvent(
                        "test",
                        null,
                        "test",
                        Level.INFO,
                        ObjectMessage("error"),
                        listOf(),
                        null
                )
        )

        assertTimeout(Duration.ofSeconds(100)) {
            while (slackFailCount == 0) {
                Thread.yield()
            }
        }
    }
}

@Configuration
class SlackTestController {
    @Bean
    fun requestSlackRouter() = router {
        POST("/slack") { req ->
            req.bodyToMono(SlackMessage::class.java)
                    .map {
                        it.copy(attachments = it.attachments.map {
                            Attachment(it.fallback, it.text.trim(), it.color)
                        })
                    }
                    .flatMap {
                        if (it == slackSuccessRequest) {
                            slackSuccessCount++
                            ServerResponse.ok().bodyValue(slackSuccessResponse)
                        } else {
                            slackFailCount++
                            ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(slackErrorResponse)
                        }
                    }
        }
    }
}
