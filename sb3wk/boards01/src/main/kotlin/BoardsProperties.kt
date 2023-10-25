package learn.sb3wk.boards

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "service")
data class BoardsProperties(
    var users: Users? = null
)

data class Users(
    var server: String? = null,
    var port: Int? = null,
    var username: String? = null,
    var password: String? = null,
)
