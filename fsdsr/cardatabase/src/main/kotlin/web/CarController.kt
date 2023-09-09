package learn.fsdsr.cardatabase.web

import learn.fsdsr.cardatabase.domain.Car
import learn.fsdsr.cardatabase.domain.CarRepository
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CarController(
    private val carRepository: CarRepository
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/cars")
    fun getCars(): Iterable<Car> {
        val all = carRepository.findAll().toList()
        logger.info("Found {} cars", all.size)
        return all
    }
}
