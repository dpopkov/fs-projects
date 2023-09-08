package learn.fsdsr.cardatabase.domain

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface CarRepository : CrudRepository<Car, Long> {
    /* Defining custom queries for the repository */
    fun findByBrand(brand: String): List<Car>
    fun findByColor(color: String): List<Car>

    /* Defining custom queries using multiple fields */
    fun findByBrandAndModel(brand: String, model: String): List<Car>
    fun findByBrandOrColor(brand: String, color: String): List<Car>

    /* Sorting using OrderBy keyword */
    fun findByBrandOrderByModelYearAsc(brand: String): List<Car>

    /* Example of query using SQL */
    @Query("select c from Car c where c.modelYear = ?1")
    fun findByModelYear(modelYear: Int): List<Car>

    /* Example of query using like */
    @Query("select c from Car c where c.brand like %?1")
    fun findByBrandEndsWith(brandEnding: String): List<Car>
}
