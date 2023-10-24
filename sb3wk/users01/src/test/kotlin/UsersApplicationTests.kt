package learn.sb3wk.users

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForObject

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation::class)
class UsersApplicationTests {

	@Value("\${local.server.port}")
	private val port = 0
	private val baseUrl = "http://localhost:"
	private val usersPath = "/users"

	@Autowired
	private val restTemplate: TestRestTemplate? = null

	private fun usersUrl(): String = "$baseUrl$port$usersPath"

	@Test
	@Order(1)
	fun `users endpoint should return collection with 2 users`() {
		val response = restTemplate?.getForObject<Collection<User>>(usersUrl())

		assertThat(response?.size).isEqualTo(2)
	}

	@Test
	@Order(2)
	fun `post new user should return user`() {
		val user = User("dummy@email.com", "Dummy")
		val response = restTemplate?.postForObject(usersUrl(), user, User::class.java)
		val users = restTemplate?.getForObject<Collection<User>>(usersUrl())

		assertThat(response).isNotNull
		assertThat(response?.email).isEqualTo("dummy@email.com")
		assertThat(users?.size).isGreaterThan(2)
	}

	@Test
	@Order(3)
	fun `find user should return user`() {
		val user = restTemplate?.getForObject<User>("${usersUrl()}/dummy@email.com")

		assertThat(user).isNotNull
		assertThat(user?.email).isEqualTo("dummy@email.com")
	}

	@Test
	@Order(4)
	fun `delete user should decrease number of users`() {
		val usersBefore = restTemplate?.getForObject<Collection<User>>(usersUrl())
		restTemplate?.delete("${usersUrl()}/dummy@email.com")
		val usersAfter = restTemplate?.getForObject<Collection<User>>(usersUrl())

		assertThat(usersBefore?.size).isEqualTo(3)
		assertThat(usersAfter?.size).isEqualTo(2)
	}

}
