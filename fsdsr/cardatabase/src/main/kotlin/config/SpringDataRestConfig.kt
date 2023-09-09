package learn.fsdsr.cardatabase.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer

@Configuration
class SpringDataRestConfig {
    @Bean
    fun repositoryRestConfigurer(): RepositoryRestConfigurer {
        return RepositoryRestConfigurer.withConfig({config ->
            config.setBasePath("/api")
        })
    }
}
