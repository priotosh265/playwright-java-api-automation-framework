# Playwright Java API Automation Framework (JUnit 5 & Cucumber BDD)

A production-grade, modular API Test Automation Framework combining **Microsoft Playwright for Java**, **Cucumber BDD**, **JUnit 5**, and **Jackson**, designed to test RESTful web services with high performance and clean BDD Gherkin specifications.

---

## 📁 Project Architecture & Directory Structure

```text
playwright-java-api-automation-framework
│
├── src
│   ├── main
│   │   └── java
│   │       ├── client
│   │       │   └── ApiClient.java          # Reusable wrapper for Playwright APIRequestContext & HTTP methods
│   │       ├── config
│   │       │   └── ConfigManager.java      # Configuration loader with environment & property overrides
│   │       ├── constants
│   │       │   └── ApiEndpoints.java       # Centralized API route definitions
│   │       ├── models
│   │       │   └── Booking.java            # POJO data models for requests and responses
│   │       └── utils
│   │           └── JsonUtils.java          # Jackson JSON serialization/deserialization utilities
│   │
│   └── test
│       ├── java
│       │   ├── base
│       │   │   └── BaseApiTest.java        # Base test class for standalone JUnit 5 API tests
│       │   ├── runners
│       │   │   └── CucumberTestRunner.java # JUnit Platform Cucumber Suite test runner
│       │   ├── stepdefinitions
│       │   │   ├── CommonSteps.java        # Shared assertions (status codes, response body codes, messages)
│       │   │   ├── ProductSteps.java       # Step definitions for products.feature
│       │   │   ├── BrandSteps.java         # Step definitions for brands.feature
│       │   │   ├── LoginSteps.java         # Step definitions for login.feature
│       │   │   ├── UserAccountSteps.java   # Step definitions for user_account.feature
│       │   │   ├── TestContext.java        # PicoContainer-compatible scenario state sharing
│       │   │   └── Hooks.java              # Lifecycle hooks & dynamic resource teardown
│       │   ├── tests
│       │   │   ├── HealthCheckTest.java    # Standalone JUnit 5 health check test
│       │   │   ├── AuthenticationTest.java # Standalone JUnit 5 auth token test
│       │   │   ├── BookingGetTest.java     # Standalone JUnit 5 GET booking tests
│       │   │   ├── BookingPostTest.java    # Standalone JUnit 5 POST create booking tests
│       │   │   ├── BookingPutTest.java     # Standalone JUnit 5 PUT update booking tests
│       │   │   ├── BookingPatchTest.java   # Standalone JUnit 5 PATCH partial update tests
│       │   │   ├── BookingDeleteTest.java  # Standalone JUnit 5 DELETE booking tests
│       │   │   └── NegativeApiTest.java    # Standalone JUnit 5 negative API tests
│       │   └── testdata
│       │       └── booking.json            # Static JSON test data for booking tests
│       │
│       └── resources
│           ├── features
│           │   ├── products.feature        # Product catalog & search API validations
│           │   ├── brands.feature          # Brand catalog & HTTP method validations
│           │   ├── login.feature           # Authentication & login negative validations
│           │   └── user_account.feature    # Full user account lifecycle & edge validations
│           │
│           ├── testdata
│           │   └── users.json              # User account & credential test data
│           │
│           └── config.properties           # Framework environment configuration
│
├── pom.xml                                 # Maven dependencies, Cucumber, and Surefire configuration
├── Jenkinsfile                             # Jenkins CI/CD declarative pipeline definition
├── .gitignore                              # Git exclusion rules
└── README.md                               # Framework documentation
```

---

## 🛠️ Technology Stack

- **Java**: Version 17+
- **HTTP Client**: [Microsoft Playwright for Java](https://playwright.dev/java/docs/api-testing) (`APIRequestContext`)
- **BDD Framework**: [Cucumber](https://cucumber.io/) (`cucumber-java`, `cucumber-picocontainer`, `cucumber-junit-platform-engine`)
- **Test Runner**: [JUnit 5 Platform Suite](https://junit.org/junit5/)
- **Serialization / Deserialization**: [Jackson Databind](https://github.com/FasterXML/jackson)
- **Assertions**: [AssertJ](https://assertj.github.io/doc/) fluent assertions & JUnit 5
- **Build Tool**: [Apache Maven](https://maven.apache.org/)
- **CI/CD Integration**: [Jenkins Declarative Pipeline](https://www.jenkins.io/doc/book/pipeline/)

---

## 🥒 Cucumber Features & BDD Coverage

### 1. `products.feature`
- **Verify all products can be retrieved**: Validates status 200, non-empty product list, and schema fields (`id`, `name`, `price`, `brand`, `category`).
- **Verify searching for a product returns matching results**: Scenario outline testing multiple search terms (`top`, `tshirt`, `jean`) and verifying response relevance.
- **Verify POST request to all products list is not allowed**: Negative validation ensuring `405 Method Not Allowed` logic is enforced.

### 2. `brands.feature`
- **Verify all brands can be retrieved**: Validates status 200 and schema fields (`id`, `brand`).
- **Verify PUT request to all brands list is not allowed**: Negative validation ensuring 405 response code.

### 3. `login.feature`
- **Verify login with invalid credentials**: Returns 404 with `"User not found!"`.
- **Verify login without email or password**: Returns 400 with `"Bad request, email or password parameter is missing in POST request."`.
- **Verify DELETE request on verifyLogin**: Returns 405 method not supported.

### 4. `user_account.feature`
- **Complete lifecycle**: Dynamic user creation with test data from `users.json`, detail verification by email, and account deletion.
- **Negative retrieval and deletion**: Validates 404 for non-existent users.

---

## ⚙️ Configuration (`src/test/resources/config.properties`)

```properties
# Base URLs
base.url=https://automationexercise.com
restful.booker.url=https://restful-booker.herokuapp.com

# Restful Booker Credentials
auth.username=admin
auth.password=password123
```

---

## 🧪 Running Tests

### 1. Run All Tests (JUnit 5 + Cucumber BDD)
```bash
mvn clean test
```

### 2. Run Only Cucumber BDD Tests
```bash
mvn test -Dtest=CucumberTestRunner
```

### 3. Run Specific Cucumber Tags
```bash
# Run only smoke scenarios
mvn test -Dtest=CucumberTestRunner -Dcucumber.filter.tags="@smoke"

# Run only products scenarios
mvn test -Dtest=CucumberTestRunner -Dcucumber.filter.tags="@products"

# Run only user lifecycle scenarios
mvn test -Dtest=CucumberTestRunner -Dcucumber.filter.tags="@lifecycle"
```

### 4. Run Standalone JUnit 5 Tests
```bash
mvn test -Dtest=HealthCheckTest
mvn test -Dtest=AuthenticationTest
mvn test -Dtest=BookingGetTest
```

### 5. Test Reports
- **Cucumber HTML Report**: `target/cucumber-reports/cucumber.html`
- **Cucumber JSON Report**: `target/cucumber-reports/cucumber.json`
- **Surefire Test Reports**: `target/surefire-reports/`
