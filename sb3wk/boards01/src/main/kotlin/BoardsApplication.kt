package learn.sb3wk.boards

import learn.sb3wk.boards.StartUp.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.Banner
import org.springframework.boot.SpringApplication
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationListener

@SpringBootApplication
class BoardsApplication

fun main(args: Array<String>) {
//    startOptions[GENERATED_BY_START_SPRING_IO]?.invoke(args)
	startOptions[SPRING_APPLICATION_BUILDER_WITH_EVENT_LISTENER]?.invoke(args)
//    startOptions[SIMPLE_USING_VALUE_CONFIGURATION]?.invoke(args)
}

enum class StartUp {
	GENERATED_BY_START_SPRING_IO,
	SIMPLE_SPRING_APPLICATION,
	SPRING_APPLICATION_BUILDER_WITH_EVENT_LISTENER,
	SIMPLE_USING_VALUE_CONFIGURATION,
}

val startOptions: Map<StartUp, (Array<String>) -> Unit> = mapOf(
	GENERATED_BY_START_SPRING_IO to { args ->
		runApplication<BoardsApplication>(*args)
	},
	SIMPLE_SPRING_APPLICATION to { args ->
		val sa = SpringApplication(BoardsApplication::class.java)
		sa.setBannerMode(Banner.Mode.OFF)
		sa.run(*args)
	},
	SPRING_APPLICATION_BUILDER_WITH_EVENT_LISTENER to { args ->
		val log: Logger = LoggerFactory.getLogger(BoardsApplication::class.java)

		SpringApplicationBuilder()
			.sources(BoardsApplication::class.java, AppConfiguration::class.java)
			.logStartupInfo(false) // default to true - logging usual "Started BoardsApplicationKt in 0.443 seconds ..."
			.bannerMode(Banner.Mode.OFF)
			.lazyInitialization(false) // default to false
			.web(WebApplicationType.NONE)
			.profiles("cloud")
			.listeners(ApplicationListener { event: ApplicationEvent ->
				log.info("Event: {}", event.javaClass.canonicalName)
			})
			.run(*args)
			/* How to pass arguments: */
            //.run("--enable", "--remote", "--option=value1", "--option=value2", "update", "upgrade")
	},
	SIMPLE_USING_VALUE_CONFIGURATION to { args ->
		runApplication<BoardsApplication>(*args)
	}
)
