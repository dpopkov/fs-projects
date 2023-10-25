# Application Users

## build.gradle

### Customize test logging

```kotlin
tasks.withType<Test> {
    testLogging {
        events("passed", "skipped", "failed") // выводит UsersApplicationTests > contextLoads() PASSED
        showExceptions = true
        exceptionFormat = TestExceptionFormat.FULL
    }
}
```

## Controller

#### Using @get:GetMapping на свойстве

```kotlin
@get:GetMapping // метод get становится обработчиком http запроса
val all: Collection<User>
    get() = users.values
```

#### Using Httpie

- POST request sending JSON:
    - `http :8080/users`
    - `http :8080/users email=first@email.com name=First`
    - `http DELETE :8080/users/first@email.com`

## Testing

#### @SpringBootTest

- use random port:
    - `@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)`

#### @Value

- Inject local port number for the current test using property `local.server.port`:
    - `@Value("\${local.server.port}") private val port = 0`
    - actual random value of port is previously set by `@SpringBootTest(webEnvironment)`

#### TestRestTemplate Methods

- getForObject
- postForObject
- delete

#### Execute tests with Gradle

- `./gradlew test`

#### Run application with Gradle

- `./gradlew bootRun`


# Application Boards

#### Run application with --debug

`./gradlew bootRun --debug`
