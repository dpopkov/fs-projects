### 3 - Use JPA to create and access database

- Use in-memory H2
- Add demo data using `CommandLineRunner`
- Define custom queries in crud repository
- About **Kotlin JPA plugin**:
  - In order to be able to use Kotlin non-nullable properties with JPA, Kotlin JPA plugin can be enabled. 
  - It generates no-arg constructors for any class annotated with @Entity, @MappedSuperclass or @Embeddable.
  - [See Building web applications with Spring Boot and Kotlin](https://spring.io/guides/tutorials/spring-boot-kotlin)
  ```
    plugins {
        kotlin("jvm") version "1.9.22"
        kotlin("plugin.spring") version "1.9.22"
        kotlin("plugin.jpa") version "1.9.22"
    }
    ```
