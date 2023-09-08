package learn.fsdsr.cardatabase.domain

import jakarta.persistence.*

@Entity
data class Car(
    val brand: String,
    val model: String,
    val color: String? = null,
    val registrationNumber: String? = null,
    val modelYear: Int? = null,
    val price: Int? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner")
    var owner: Owner? = null,
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
)
