package learn.fsdsr.cardatabase

import learn.fsdsr.cardatabase.domain.Car
import learn.fsdsr.cardatabase.domain.CarRepository
import learn.fsdsr.cardatabase.domain.Owner
import learn.fsdsr.cardatabase.domain.OwnerRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CardatabaseApplication(
    private val carRepository: CarRepository,
    private val ownerRepository: OwnerRepository,
) : CommandLineRunner {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun run(vararg args: String?) {
        val owners = listOf(
            Owner(firstname = "Jane", lastname = "Doe"),
            Owner(firstname = "Jack", lastname = "Sparrow"),
        )
        ownerRepository.saveAll(owners)
        val data = initialData(owners)
        carRepository.saveAll(data)
        logger.debug("saved initial data (${data.size} items)")
        for(car in carRepository.findAll()) {
            logger.info("brand: {}, model: {}", car.brand, car.model)
        }
    }

    private fun initialData(owners: List<Owner>): List<Car> = listOf(
        Car(
            brand = "BMW",
            model = "X5",
            color = "Black",
            registrationNumber = "ADF-1111",
            modelYear = 2023,
            price = 49_000,
            owner = owners[0],
        ),
        Car(
            brand = "Nissan",
            model = "Leaf",
            color = "White",
            registrationNumber = "SSJ-2222",
            modelYear = 2020,
            price = 29_000,
            owner = owners[1],
        ),
        Car(
            brand = "Toyota",
            model = "Prius",
            color = "Silver",
            registrationNumber = "KKO-3333",
            modelYear = 2022,
            price = 39_000,
            owner = owners[1],
        )
    )
}

fun main(args: Array<String>) {
    runApplication<CardatabaseApplication>(*args)
}
