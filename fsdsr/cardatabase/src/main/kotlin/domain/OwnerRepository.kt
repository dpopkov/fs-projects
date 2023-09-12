package learn.fsdsr.cardatabase.domain

import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface OwnerRepository : CrudRepository<Owner, Long> {
    fun findByFirstname(firstname: String): Optional<Owner>
}
