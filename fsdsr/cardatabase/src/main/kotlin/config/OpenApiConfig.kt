package learn.fsdsr.cardatabase.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {
    @Bean
    fun carDatabaseOpenAPI(): OpenAPI? {
        return OpenAPI()
            .info(
                Info()
                    .title("Car REST API")
                    .description("Car stock")
                    .version("1.0")
            )
    }
}
