package learn.fsdsr.cardatabase.domain

/**
 * Stores credentials for authentication.
 */
data class AccountCredentials(
    val username: String,
    val password: String,
)
