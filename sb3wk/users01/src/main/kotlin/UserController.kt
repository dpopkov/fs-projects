package learn.sb3wk.users

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController {
    private val users: MutableMap<String, User> = mutableMapOf(
        User("jane@email.com", "Jane").let { it.email!! to it},
        User("Jack@email.com", "Jack").let { it.email!! to it},
    )

    @get:GetMapping
    val all: Collection<User>
        get() = users.values

    @GetMapping("/{email}")
    fun findUserByEmail(@PathVariable("email") email: String): User? {
        return users[email]
    }

    @PostMapping
    fun save(@RequestBody user: User): User {
        users[user.email!!] = user
        return user
    }

    @DeleteMapping("/{email}")
    fun deleteByEmail(@PathVariable("email") email: String) {
        users.remove(email)
    }
}
