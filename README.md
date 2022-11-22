# Spring Boot with Kotlin & JUnit 5 tutorial

Author : Sunho Hong (https://github.com/sunhohong)
> note : This is a recap of the tutorial

### Reference

This tutorial project is based on following series of Youtube videos:

* [Spring Boot with Kotlin & JUnit 5 - Tutorials](https://www.youtube.com/playlist?list=PL6gx4Cwl9DGDPsneZWaOFg0H2wsundyGr)

## Prerequisites

* JDK 17
* IntelliJ IDEA
* Gradle 7.3
* Spring Boot 2.5.6
* Kotlin 1.6.0
* JUnit 5.8.1
* Spring Web 2.5.6
* Spring Boot DevTools 2.5.6

## Getting Started

* Create a new project with Spring Initializr (https://start.spring.io/)
  ![Spring Initializr](readme_resources/spring_initializr.png "Spring Initializr")

### The model

**Bank**

* Kotlin provides getter and setter methods by default, but we can make getter and setter if it necessary
* Using `val` to make it read-only.
* Kotlin's data class doesn’t need toString(), equals(), hashCode() cause those functions are auto generated
  ```kotlin
    data class Bank(
      val accountNumber: String,
      val trust: Double,
      val transactionFee: Int
    )
  ```

**Data Source**

### Service layer

* Create a service layer to handle business logic.
* Service layer could handle complex logics but in this case it just connects to the data source and controller layer.
* `BankService` interface
  ```kotlin
    interface BankService {
      fun getBanks(): Collection<Bank>
      fun getBank(accountNumber: String): Bank
      fun addBank(bank: Bank): Bank
      fun updateBank(bank: Bank): Bank
      fun deleteBank(accountNumber: String)
    }
  ```

### Controller layer (Web layer)

* Controller using `@RestController` annotation to make it a REST controller.

## Test

### without IDE

* Run `./gradlew test` to run all tests

### with IDE

* Run test on IDE menu `Run > Run 'All Tests'`

## IDE Settings

### Live Template

**Test**

```Kotlin
@Test
fun `should $DESCRIPTION$`() {
    // given
    $GIVEN$
    // when
    $WHEN$
    // then
    $THEN$
}

```

**Nested test**

```Kotlin
@Nested
@DisplayName("$DESCRIPTION$")
@TestInstance(Lifecycle.PER_CLASS)
inner class $CLASS_NAME$ {
    $CONTENT$
}
```

## Tips

* If the project doesn't build with Java version problem, try to change the version from project structure settings. (
  find under file menu or CMD + ;)
  ![Project Structure](readme_resources/project-structure.png "Project Structure")
* If the project doesn't build with Gradle version problem, try to change the JDK path in .zshrc (or .bashrc) file.
* Set up [Code style](https://ddolcat.tistory.com/526) and [Save action](https://devroach.tistory.com/73) in IDE
  settings.
* If `gradlew bootBuildImage` shows `Connection to the Docker daemon at 'localhost' failed with error` error, check
  Docker is running on local machine.

# Tutorial Summary

## Tutorial 2 - Hello World

### What's new?

- [x] Add the `HelloWorldController`
- [x] Update `README`
- [x] Add `readme_resources` directory for README file

## Tutorial 3 - Project Structure

### What's new?

### What's in the tutorial

* Dependencies, plugins and version/env descriptions are in `build.gradle.kts` file.
* Project settings are in `settings.gradle.kts`
* Build and test the project using `gradlew build` command without IDE
* Server port can be changed by `application.properties` file
  ```
  server.port=9000
  ```

## Tutorial 4 - Data Layer

### What's new?

- [x] The Bank model
- [x] Refactor the Bank model
    - Move attributes definition to Primary constructor
    - Using Kotlin data class

### What's in the tutorial

* Kotlin provides getter and setter methods by default, but we can make getter and setter if it necessary.
* Primary constructor can be placed just right after the class definition like params.
  ```Kotlin
  data class Bank(
    val accountNumber: String,
    val trust: Double,
    val transactionFee: Int
  )
  ```
* Kotlin data class doesn’t need toString(), equals(), hashCode() cause those functions are auto generated

## Tutorial 5 - Data Source

### What's new?

- [x] Add BankDataSource interface
- [x] Add MockBankDataSource class
- [x] Add MockBankDataSourceTest

### What's in the tutorial

* `@Repository` annotation notifies functionality of the class to Spring
    * This class is responsible for retrieving data, storing data and those kind of functionalities.
      ```Kotlin
      @Repository
      class MockBankDataSource : BankDataSource
      ```

### Tips

* CMD + Shift + T : **Create Test** (Should be cursor on class name or method name)
* Ctrl + R : **Rerun last command** (It is useful when test again)

## Tutorial 6 - Service Layer

### What's new?

- [x] Add BankService class
- [x] Add BankServiceTest class
- [x] Add [Mockk - mocking library for Kotlin](https://mockk.io/)

### What's in the tutorial

- Add Mockk to mock a component in other layer.
- Set `relaxed = true` when calling mockk to prevent occurring errors cause mockk doens't know what should be returned.
    - If specific return value is needed, we can use stub, but other cases just using relaxed mock
      ```
      private val dataSource: BankDataSource = mockk(relaxed = true)
      ```

## Tutorial 7 - Web Layer

### What's new?

- [x] Add BankController class
- [x] Add BankControllerTest class

### What's in the tutorial

* `@RequestMapping` annotation makes route for the application.
* `@GetMapping` annotation makes a function to work as a REST GET method.
* `@SpringBootTest` annotation is needed to test a controller, it will set up the application context.
  ```Kotlin
  @SpringBootTest
  internal class BankControllerTest
  ```
* `MockMvc` does mocking http request, so it works as a request provider.
* `@Autowired` annotation matches beans from Spring container to the target variable automatically.
* But `@AutoConfigureMockMvc` annotation should be applied to the class
  ```Kotlin
  @SpringBootTest
  @AutoConfigureMockMvc
  internal class BankControllerTest {

      @Autowired
      lateinit var mockMvc: MockMvc
  ```

### Tips

- [Kotlin] `lateinit` keyword initialize a variable after instance created, so it accelerate the application's boot
  process.

## Tutorial 8 - GET Single Bank

### What's new?

- [x] Add `getBank()` method to BankController
- [x] Add test for `getBank()` method to BankControllerTest
- [x] Add negative case test for `getBank()` method to BankControllerTest
- [x] Add Exception handler to BankController for handling `NoSuchElementException` in controller layer

### What's in the tutorial

* `@GetMapping` annotation with path variable to handle dynamic path variable
* `@PathVariable` annotation to get the path variable from the request URL
  ```Kotlin
    @GetMapping("/{accountNumber}")
    fun getBank(@PathVariable accountNumber: String): Bank {
  ```
* Refactor tests in `BankControllerTest` to group tests by `@Nested` annotation
* `@Nested` annotation and `inner class` are used to group tests.
* Add live template for nested test. See [Live Template](#live-template) section.
* Refactor `/api/bank` url to `baseUrl` constant in `BankControllerTest`

## Tutorial 9 - Post endpoint

### What's new?

- [x] Add POST endpoint to `BankController`.
- [x] Add test for POST endpoint to `BankControllerTest`.
- [x] Add `ObjectMapper` bean to `BankControllerTest` to convert `Bank` object to JSON string.
- [x] Add `IllegalArgumentException` handler to `BankController` to handle request with existing account number.

### What's in the tutorial

* Refactor `mockMvc` member variable to `@Autowired` primary constructor in `BankControllerTest`.
* Refactor assert object contents statements in `BankControllerTest` to use `ObjectMapper`.
* Change mock banks list to `mutableListOf` to add new bank.
    * (Kotlin basic) In Kotlin, `listOf` is immutable list, so it can't be changed.
    * `mutableListOf` is mutable list, so it can be changed directly.
    * If you want to use immutable list on this case, you should return new list with added or removed item.
      > From [Effective Kotlin](http://www.yes24.com/Product/Goods/106225986)

## Tutorial 10 - Patch endpoint

### What's new?

- [x] Add Patch endpoint to `BankController`.
- [x] Add test for Patch endpoint to `BankControllerTest`.

### What's in the tutorial

* Nothing special. Just add patch endpoint and test.
* Refactor assert object contents statements in `BankControllerTest` to use `ObjectMapper` was in this tutorial. not in
  the tutorial 9.

## Tutorial 11 - DELETE endpoint

### What's new?

- [x] Add Delete endpoint to `BankController`.
- [x] Add test for Delete endpoint to `BankControllerTest`.

### What's in the tutorial

* Nothing special. Just add delete endpoint and test.
* [Kotlin] `Unit` is a return type same as `void` in Java.

## Tutorial 12 - Testing best practices

### What's new?

* [x] Refactor test using `@DirtiesContext` fit to "Isolated" principle of F.I.R.S.T. principles.

### What's in the tutorial

**First Principles for Automated Tests**

* :white_check_mark: **Fast** - fast feedback loop, run often
* :white_check_mark: **Isolated** - independent, arbitrary order.
* :white_check_mark: **Repeatable** - same result each time,not flacky
* :white_check_mark: **Self-validating** - actual vs. expected
* :white_check_mark: **Timely** - with (or even before!) production code

* `@DirtiesContext` annotation is used to reset the context after each test. But it decreases the performance of tests.

## Tutorial 13 - REST APIs with RestTemplate & Dependency Injection

### What's new?

- [x] Add `NetworkDataSource` class.

### What's in the tutorial

* How to use DI to select specific implementation of interface if there are multiple implementations.
* Disclaimer : Currently this tutorial is not working because the endpoint in the tutorial is not available anymore.

## Tutorial 14 - Gradle for building Docker images and Boot JARs

### What's new?

- [x] Refactor `BankControllerTest` to fit changes in tutorial 13.
- [x] Create a Jar file with `bootJar` task.
- [x] Create a Docker image with `bootBuildImage` task.

### What's in the tutorial

* Gradle is a build tool for Java, Kotlin and maybe other languages.
* Gradle also used to automation I think it's like `rake` in ROR of this case.
    * Default Gradle tasks include `build`, `clean`, `test`, `assemble`, `jar`, `bootJar`, `bootRun`, `bootBuildImage`
      , `docker` and many more.
* `gradlew bootRun` command is used to run the application in terminal directly.
* `gradlew bootJar` makes a jar file in `build/libs` directory. It can be run with `java -jar` command.
* There are three different ways to run the application without IDE.
    1. Run the application with `gradlew bootRun` command.
    2. Run the application with `java -jar` command.
    3. Run the application with Docker image.