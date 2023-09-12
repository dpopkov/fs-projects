package learn.fsdsr.cardatabase.web

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    /* This test needs running db instance containing username and password. */
    @Test
    fun testAuthentication() {
        mockMvc.perform(
            post("/login")
                .content("""{"username":"user","password":"user"}""")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
        ).andDo(org.springframework.test.web.servlet.result.MockMvcResultHandlers.print())
            .andExpect(status().isOk())
    }
}
