package learn.sb3wk.boards

import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@EnableConfigurationProperties(BoardsProperties::class)
@Configuration
class BoardsConfiguration {
    private val log = LoggerFactory.getLogger(this::class.java)

    @Bean
    fun init(props: BoardsProperties): ApplicationListener<ApplicationReadyEvent> {
        return ApplicationListener { _: ApplicationReadyEvent? ->
            log.info(
                """
                The Users service properties are:
                - Server: {}
                - Port: {}
                - Username: {}
                - Password: {}
            """.trimIndent(),
                props.users?.server,
                props.users?.port,
                props.users?.username,
                props.users?.password,
            )
        }
    }

}
