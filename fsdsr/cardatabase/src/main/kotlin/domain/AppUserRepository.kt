package learn.fsdsr.cardatabase.domain

import org.springframework.data.repository.CrudRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import java.util.Optional

@RepositoryRestResource(exported = false)
interface AppUserRepository : CrudRepository<AppUser, Long> {
    /**
     * Find a user by the specified username from the database
     * in the authentication process.
     */
    fun findByUsername(username: String): Optional<AppUser>
}
