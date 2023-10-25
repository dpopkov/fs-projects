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


## Spring Boot Features

#### Banner

- Banner: `banner.txt` file in `resources`
- Turn banner off:

```kotlin
val sa = SpringApplication(BoardsApplication::class.java)
sa.setBannerMode(Banner.Mode.OFF)
sa.run(*args)
```

#### SpringApplicationBuilder

```kotlin
SpringApplicationBuilder()
    .sources(BoardsApplication::class.java)
    .logStartupInfo(false) // default to true - logging usual "Started BoardsApplicationKt in 0.443 seconds ..."
    .bannerMode(Banner.Mode.OFF)
    .lazyInitialization(true) // default to false
    .web(WebApplicationType.NONE)
    .profiles("cloud")
    .listeners(ApplicationListener { event: ApplicationEvent ->
        log.info("Event: {}", event.javaClass.canonicalName)
    })
    .run()
```

#### Application Arguments

##### Getting arguments

```kotlin
@Configuration
class AppConfiguration(arguments: ApplicationArguments) {
    init {
        log.info("Option Args: {}", arguments.optionNames)
        log.info("Option Arg Values: {}", arguments.getOptionValues("option"))
        log.info("Non Option: {}", arguments.nonOptionArgs)
    }
}
```

##### Running with Gradle:

`./gradlew bootRun --args="--enable --remote --option=value1 --option=value2 update upgrade"`

#### Executable JAR

- Create: `./gradlew build`
- Run: `java -jar app.jar`
- Run with arguments: `java -jar app.jar --enable --remote --option=value1 upgrade`

#### Before Application is ready these can execute code

1. ApplicationRunner использует `ApplicationArguments`
2. CommandLineRunner использует `Array<String>`
3. `ApplicationListener<ApplicationEvent>` использует `ApplicationEvent` и его потомков
  - ApplicationReadyEvent

### External Configuration

#### Simple base solution using @Value

- values in application.properties : `users.server=127.0.0.1`
- Properties in Configuration class : `@Value("\${users.server}") var server: String? = null`
- Use `ApplicationListener<ApplicationReadyEvent>` bean to log properties.

#### Spring Boot solution using @ConfigurationProperties

- `@ConfigurationProperties` binds the properties to every field in the class
- `@ConfigurationProperties(prefix = "service")` - класс должен содержать fields for properties
  - property должно содержать префикс указанный в аннотации
- `@EnableConfigurationProperties(BoardsProperties::class)` - конфигурация должна объявлять класс содержащий properties
  - `fun init(props: BoardsProperties)` - это класс может быть injected

#### Configuration Precedence
- default properties (`SpringApplication.setDefaultProperties`)
- `@PropertySource` on `@Configuration` class
- Config data (**application.properties**):
  - `service.users.port=8080`
- `RandomValuePropertySource`
- **OS environment variables**:
  - `SERVICE_USERS_PORT=8080`
- Java System properties (`System.getProperties()`)
- JNDI attributes
- ServletContext init parameters
- ServletConfig init parameters
- Properties from SPRING_APPLICATION_JSON (`-Dspring.application.json='{"server.port":8082}'`)
- **Command-line arguments**:
  - `java -jar app.jar --service.users.port=1234`
  - `./gradlew bootRun --args="--service.users.port=1234"`
- `properties` attribute on tests (available on `@SpringBootTest`)
  - `@SpringBootTest(properties = ["service.users.port=1234"])`
  - `@SpringBootTest(properties = ["--service.users.port=1234"])`
- `@TestPropertySource` **аннотация на тестовых классах**:
  - `@TestPropertySource("classpath:application123.properties")` - файл properties
  - `@TestPropertySource(properties = ["service.users.port=1234"])` - inline properties
  - имеет много возможностей, см.документацию.
- Devtools global settings

#### Examples of overriding default properties
```bash
java -jar app.jar --service.users.username=user42
SERVICE_USERS_USERNAME=user42 java -jar app.jar
java -Dspring.application.json='{"server.port":8082}' -jar app.jar
```

#### Properties in file application.properties or YAML variant Precedence
- application.properties packaged **inside** JAR
- application-{profile}.properties packaged **inside** JAR
- file application.properties **outside** packaged JAR
- file application-{profile}.properties **outside** packaged JAR

#### Specifying location of properties
- `--spring.config.location=<location>`

### Application Profiles
- `application-<profile>.properties`
- Activation:
  - `SpringApplicationBuilder.profiles("cloud")`
  - `SPRING_PROFILES_ACTIVE=cloud`
  - `--spring.profiles.active="cloud"`
