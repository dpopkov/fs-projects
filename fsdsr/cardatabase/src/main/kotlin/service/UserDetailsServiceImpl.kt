package learn.fsdsr.cardatabase.service

import learn.fsdsr.cardatabase.domain.AppUserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val appUserRepository: AppUserRepository
) : UserDetailsService  {
    override fun loadUserByUsername(username: String?): UserDetails {
        if (username == null) {
            throw IllegalArgumentException("Username cannot be null")
        }
        val userOpt = appUserRepository.findByUsername(username)
        if (userOpt.isPresent) {
            val user = userOpt.get()
            return User.withUsername(username)
                .password(user.password)
                .roles(user.role)
                .build()
        } else {
            throw UsernameNotFoundException("User not found")
        }
    }
}
