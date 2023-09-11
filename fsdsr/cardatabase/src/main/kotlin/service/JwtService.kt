package learn.fsdsr.cardatabase.service

import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import java.security.Key

/**
 * Generates and verifies a signed JWT.
 */
@Component
class JwtService {
    private val logger = LoggerFactory.getLogger(this::class.java)

    /** Token expires after 1 day in milliseconds */
    val expirationTime = 24 * 60 * 60 * 1000
    val prefix = "Bearer "

    /**
     * Generated secret key only for demonstration purposes.
     * In production, it should be read from the application configuration.
     */
    val key: Key = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    fun generateSignedToken(username: String): String = io.jsonwebtoken.Jwts.builder()
        .setSubject(username)
        .setExpiration(java.util.Date(System.currentTimeMillis() + expirationTime))
        .signWith(key)
        .compact()

    /**
     * Gets a token from the [request]'s Authorization header,
     * verifies the token and gets the username.
     */
    fun getVerifiedAuthUser(request: HttpServletRequest): String {
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (authHeader != null) {
            if (!authHeader.startsWith(prefix)) {
                throw IllegalArgumentException("Invalid prefix of the authorization header: $authHeader")
            }
            val token = authHeader.substring(prefix.length)
            val user: String? = io.jsonwebtoken.Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body
                .subject
            if (user != null) {
                return user
            }
            logger.debug("Failed to parse user from the token: $token")
            throw IllegalArgumentException("Failed to parse user")
        } else {
            throw IllegalArgumentException("Authorization header is missing")
        }
    }
}
