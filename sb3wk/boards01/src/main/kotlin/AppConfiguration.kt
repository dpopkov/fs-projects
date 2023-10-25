package learn.sb3wk.boards

import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfiguration(arguments: ApplicationArguments) {
    private val log = LoggerFactory.getLogger(this::class.java)

    // SpringApplicationBuilder should run .lazyInitialization(false) for this configuration to work.
    init {
        log.info("Option Args: {}", arguments.optionNames)
        log.info("Option Arg Values: {}", arguments.getOptionValues("option"))
        log.info("Non Option: {}", arguments.nonOptionArgs)
    }
}
