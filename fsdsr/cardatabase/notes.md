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

### 4 - Creating a RESTful Web Service

- Create `CarController` with `@GetMapping("/cars")`
- To avoid problems with infinite serialization add ignoring:
  - `@JsonIgnore var cars: List<Car>`
- Use Spring Data REST (creates web services for entities automatically):
  - add dependency `org.springframework.boot:spring-boot-starter-data-rest`
  - config base path:
    - `spring.data.rest.basePath=/api` - NOT working!
    - add `SpringDataRestConfig` with `config.setBasePath("/api")` - it works ok.
- Documenting automatically with OpenAPI 
  - add dependency `org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.4`
    - Versioning issues: 
      - version 2.0.2 fails with "Failed to load API definition" message and NPE somewhere in `org.springdoc.core.data.DataRestOperationService`.
      - version 2.0.4 with Spring Boot 3.1.4 and spring.dependency-management 1.1.3 is OK.
      - version 2.0.4 with Spring Boot 3.3.7 and spring.dependency-management 1.1.7 FAILS.
      - versions 2.6.0 and 2.7.0 with Spring Boot 3.3.7 FAILS.
      - version 2.6.3 with Spring Boot 3.4.2 works but with exceptions in `io.swagger.v3.core`
  - make configuration in `OpenApiConfig`
  - add OpenAPI configuration in application.properties
  - run and go to `http://localhost:8080/swagger-ui.html`

### 5 - Add Spring Security

- Add default Spring Security
  - Add dependencies:
    - `org.springframework.boot:spring-boot-starter-security`
    - `org.springframework.security:spring-security-test`
  - Run the app and behold generated user password.
  - Go to `http://localhost:8080/api` and behold login page.
  - Enter `user` and generated password.
  - Enjoy Basic Authentication.
- Add configuration in `SecurityConfig`
  - Add in-memory user in `userDetailsService()`
  - Run the app and use in-memory user.
- Implement getting users from database:
  - create `AppUser` entity and `AppUserRepository`
    - with `findByUsername(username: String)` for authentication process
  - create `UserDetailsServiceImpl` to get user details from database user.
- Modify `SecurityConfig` to disable in-memory users and enable users from database
  - delete `userDetailsService()`
  - use `UserDetailsServiceImpl`
  - add `configureGlobal(auth)` to use `userDetailsService`
- Save test users with hashed passwords in CommandLineRunner
- Disable access to the generated URL `api/appUsers`:
  - by setting `@RepositoryRestResource(exported = false)` on AppUserRepository
- Switch from Basic Authentication to JSON Web Token
  - Add dependencies for `jjwt`
    - `implementation("io.jsonwebtoken:jjwt-api:0.11.5")`
    - `runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")`
    - `runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")`
- Secure the login
  - Add `JwtService`
    - generate a signed JWT
    - get a token from the auth header
  - Add `AccountCredentials` to store credentials for authentication
  - Add `LoginController`
    - use JwtService and AuthenticationManager
    - generate token and send it in the response Authorization header
  - Configure security in `SecurityConfig`: 
    - add `AuthenticationManager` bean
    - implement `filterChain` method:
      - never create session and therefore disable csrf
      - allow POST to `/login`
      - authenticate all others
  - Run and POST to `http://localhost:8080/login` with username and password in json body
    - behold the signed jwt in the response Authorization header
- Secure other requests
  - implement `AuthenticationFilter`
  - inject `AuthenticationFilter` to `SecurityConfig`
    - add the filter in `filterChain`
  - Run and POST to `http://localhost:8080/login` with credentials, get the token in the response Authorization header.
  - Use the token in all other requests.
- Handle exceptions
  - implement `AuthEntryPoint`
  - inject it in `SecurityConfig` 
    - config exception handling in `filterChain`
  - try to go to any url w/o authentication
    - behold `401 Unauthorized` error with body `Error: Full authentication is required to access this resource`
  - get a fresh token at `/login`, then enjoy authenticated requests
- Add CORS filter
  - add `CorsConfigurationSource` bean to `SecurityConfig`
  - configure cors in `filterChain`

### 6 - Testing Backend

- Ensure dependencies:
  - `testImplementation("org.springframework.boot:spring-boot-starter-test")`
  - `testRuntimeOnly("com.h2database:h2")`
- Add JPA tests:
  - create `OwnerRepositoryTest` with `@DataJpaTest`
    - When using this annotation, the H2 database and Spring Data are automatically configured for testing
  - add save and delete tests
- Add `CarControllerTest` with `@AutoConfigureMockMvc` 
  - this test needs running postgres (fix it)

### 12 - Setting Up the Frontend

- Prepare the backend
  - Make unsecure config to start frontend development
