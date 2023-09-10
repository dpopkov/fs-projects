package learn.fsdsr.cardatabase

import learn.fsdsr.cardatabase.domain.*
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CardatabaseApplication(
    private val carRepository: CarRepository,
    private val ownerRepository: OwnerRepository,
    private val appUserRepository: AppUserRepository,
) : CommandLineRunner {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun run(vararg args: String?) {
        if (carRepository.count() == 0L) {
            logger.debug("no cars found, populating")
            populateCars()
        } else {
            logger.debug("cars found, not populating")
        }
        if (appUserRepository.count() == 0L) {
            logger.debug("no test users found, populating")
            populateUsers()
        } else {
            logger.debug("test users found, not populating")
        }
    }

    fun populateUsers() {
        appUserRepository.save(AppUser(
            username = "user",
            password = "$2y$10\$CZ22MudZ909OZxpoEj7Mde8UhKujnfP0X73tb5tsLRZHzuP0xmEPW",
            role = "USER"
        ))
        appUserRepository.save(AppUser(
            username = "admin",
            password = "$2y$10\$E1c1v/XGsatv7FsMjGKEuepRuUiTwGIYA3ssy/gsZDjvBhuG6XhFq",
            role = "ADMIN"
        ))
    }

    private fun populateCars() {
        val owners = listOf(
            Owner(firstname = "Jane", lastname = "Doe"),
            Owner(firstname = "Jack", lastname = "Sparrow"),
        )
        ownerRepository.saveAll(owners)
        val data = initialData(owners)
        carRepository.saveAll(data)
        logger.debug("saved initial data (${data.size} items)")
        for (car in carRepository.findAll()) {
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
