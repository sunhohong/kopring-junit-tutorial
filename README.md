# Spring Boot with Kotlin & JUnit 5 tutorial

Author : Sunho Hong (https://github.com/sunhohong)
> note : This is a recap of the tutorial

### Reference

This tutorial project is based on following series of Youtube videos:

* [Spring Boot with Kotlin & JUnit 5 - Tutorials](https://www.youtube.com/playlist?list=PL6gx4Cwl9DGDPsneZWaOFg0H2wsundyGr)

### Prerequisites

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

### Test

#### without IDE

* Run `./gradlew test` to run all tests

#### with IDE

* Run test on IDE menu `Run > Run 'All Tests'`

### IDE Settings

#### Live Template

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

### Tips

* If the project doesn't build with Java version problem, try to change the version from project structure settings. (
  find under file menu or CMD + ;)
  ![Project Structure](readme_resources/project-structure.png "Project Structure")
* If the project doesn't build with Gradle version problem, try to change the JDK path in .zshrc (or .bashrc) file.
* Set up [Code style](https://ddolcat.tistory.com/526) and [Save action](https://devroach.tistory.com/73) in IDE
  settings.

# Tutorial Summary

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