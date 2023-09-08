package learn.fsdsr.cardatabase.domain

import jakarta.persistence.*

@Entity
data class Owner(
    val firstname: String,
    val lastname: String,
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "owner")
    var cars: List<Car> = emptyList(),
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val ownerId: Long? = null,
) {
    override fun toString(): String {
        return "Owner(firstname='$firstname', lastname='$lastname', ownerId=$ownerId)"
    }
}
