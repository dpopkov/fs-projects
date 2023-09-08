package learn.fsdsr.cardatabase

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CardatabaseApplication

private val logger = LoggerFactory.getLogger(CardatabaseApplication::class.java)

fun main(args: Array<String>) {
	runApplication<CardatabaseApplication>(*args)
	logger.debug("CardatabaseApplication started with arguments: ${args.joinToString()}")
}
