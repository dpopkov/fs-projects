package learn.fsdsr.cardatabase.config

import learn.fsdsr.cardatabase.auth.AuthEntryPoint
import learn.fsdsr.cardatabase.auth.AuthenticationFilter
import learn.fsdsr.cardatabase.service.UserDetailsServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
    val userDetailsService: UserDetailsServiceImpl,
    val authenticationFilter: AuthenticationFilter,
    val exceptionHandler: AuthEntryPoint,
) {
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder())
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager =
        authConfig.authenticationManager

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        /* Temporary version of unsecure config for simplified frontend development */
        /*
        http.csrf {
            it.disable()
        }.cors(
            Customizer.withDefaults()
        ).authorizeHttpRequests {
            it.anyRequest().permitAll()
        }
        */

        /* placeholder for commenting secure config out */
        http.csrf {
            it.disable()
        }.cors(
            Customizer.withDefaults()
        ).sessionManagement {
            it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }.authorizeHttpRequests {
            it.requestMatchers(HttpMethod.POST, "/login").permitAll()
                .requestMatchers("/api/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/**").permitAll()
                .anyRequest().authenticated()
        }.addFilterBefore(
            authenticationFilter,
            UsernamePasswordAuthenticationFilter::class.java
        ).exceptionHandling {
            it.authenticationEntryPoint(exceptionHandler)
        }
        /* placeholder for commenting secure config out */
        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val config = CorsConfiguration().apply {
            allowedOrigins = listOf("*")
            allowedMethods = listOf("*")
            allowedHeaders = listOf("*")
            allowCredentials = false
            applyPermitDefaultValues()
        }
        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", config)
        }
    }
}
