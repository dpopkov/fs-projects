package learn.fsdsr.cardatabase.web

import learn.fsdsr.cardatabase.domain.AccountCredentials
import learn.fsdsr.cardatabase.service.JwtService
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController(
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager,
) {
    @PostMapping("/login")
    fun login(@RequestBody credentials: AccountCredentials): ResponseEntity<Any> {
        val creds = UsernamePasswordAuthenticationToken(credentials.username, credentials.password)
        val auth: Authentication = authenticationManager.authenticate(creds)

        val jwts: String = jwtService.generateSignedToken(auth.name)

        // Build response with the generated token
        return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, "Bearer $jwts")
            .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization")
            .build()
    }
}
