package learn.fsdsr.cardatabase.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class OwnerRepositoryTest {
    @Autowired
    private lateinit var ownerRepository: OwnerRepository

    @Test
    fun saveOwner() {
        ownerRepository.save(Owner("test-firstname", "test-lastname"))
        assertThat(ownerRepository.findByFirstname("test-firstname").isPresent)
            .isTrue()
    }

    @Test
    fun deleteOwners() {
        ownerRepository.save(Owner("test-firstname1", "test-lastname1"))
        ownerRepository.save(Owner("test-firstname2", "test-lastname2"))
        ownerRepository.deleteAll()
        assertThat(ownerRepository.count()).isEqualTo(0)
    }
}
