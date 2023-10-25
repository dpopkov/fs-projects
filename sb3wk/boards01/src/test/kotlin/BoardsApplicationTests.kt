package learn.sb3wk.boards

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource

@SpringBootTest
@TestPropertySource("classpath:test.properties")
class BoardsApplicationTests {

	@Test
	fun contextLoads() {
	}

}
