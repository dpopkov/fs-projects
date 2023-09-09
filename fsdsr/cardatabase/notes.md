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
- Set up PostgreSQL database 
  - Run PostgreSQL in Docker
    - `cd deploy`
    - start: `docker-compose -f docker-compose-postgres.yml up -d`
    - stop: `docker-compose -f docker-compose-postgres.yml down`
- Add PostgreSQL dependency:
  - `runtimeOnly("org.postgresql:postgresql")`
- Config db properties:
  ```properties
  spring.datasource.url=jdbc:postgresql://localhost:5432/fsdsr-cardb
  spring.datasource.username=postgres
  spring.datasource.password=fsdsr-cardb-pass
  spring.datasource.driver-class-name=org.postgresql.Driver
  spring.jpa.generate-ddl=true
  spring.jpa.hibernate.ddl-auto=update
  ```
