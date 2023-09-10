package learn.fsdsr.cardatabase.domain

import org.springframework.data.repository.CrudRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import java.util.Optional

@RepositoryRestResource(exported = false)
interface AppUserRepository : CrudRepository<AppUser, Long> {
    fun findByUsername(username: String): Optional<AppUser>
}
