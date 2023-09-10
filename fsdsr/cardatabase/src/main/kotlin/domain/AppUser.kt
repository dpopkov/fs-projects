package learn.fsdsr.cardatabase.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class AppUser(
    @Column(nullable = false, unique = true)
    val username: String,
    @Column(nullable = false)
    val password: String,
    @Column(nullable = false)
    val role: String,
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    val id: Long? = null,
)
