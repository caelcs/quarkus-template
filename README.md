# quarkus-template

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

The goal of ths template is to give the engineers a quick look how to use the most common
libraries.

- ✅ Exposing REST Endpoints
- ❌ Pagination
- ❌ Audit for tables
- ❌ Events and outbox pattern
- ✅ Database Integration
- ✅ Connection poll for database
- ✅ Declaring beans in configuration classes
- ✅ Exposing Metrics Endpoints
- ✅ Exposing Health Endpoints
- ✅ Use of REST Clients with a connection poll
- ✅ Generating and Exposing Swagger
- ✅ Using Hexagonal Architecture
- ✅ Use of Yaml Configuration Files
- ✅ Loading configurations from yaml to class
- ✅ Use of MapStruct
- ✅ Use of logging and log levels
- ❌ Define metrics for the endpoints
- ❌ Semantic versioning
- ✅ Error handling

In terms of testing:

- ✅ Integration Tests (JVM and Native mode)
- ✅ Using Mocks
- ✅ Use of Architecture Unit Test
- ✅ Use of Wiremock for stubs
- ❌ Use of GatLing for Stress Tests
- ✅ Use of BDD and JGiven as Native Integration Test

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./gradlew quarkusDev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./gradlew build
```

It produces the `quarkus-run.jar` file in the `build/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `build/quarkus-app/lib/` directory.

The application is now runnable using `java -jar build/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./gradlew build -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar build/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./gradlew build -Dquarkus.native.enabled=true
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./gradlew build -Dquarkus.native.enabled=true -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./build/quarkus-template-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/gradle-tooling>.

## Related Guides

- REST ([guide](https://quarkus.io/guides/rest)): A Jakarta REST implementation utilizing build time processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.

## Provided Code

### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)

## Running Native Tests

By default at the time of creating this template Quarkus is using PROD profile for building the native image and running the native tests.

In order to run these testing against the native image using test config, I have created a profile called native-test with an specific application yaml file which
override the database for a h2.

```shell
./gradlew clean testNative -Dquarkus.profile=native-test
```

