# quarkus-template

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

The goal of ths template is to give the engineers a quick look how to use the most common
libraries.

- ✅ Exposing REST Endpoints
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
- ✅ Logging Json for PROD
- ✅ MDC to generate correlation id and passing it to the REST client
- ✅ Error handling
- ✅ Dependencies update detection
- ✅ Use github actions for CI at pull request, main and release branches
- ✅ Attached native binary to the release on github
- ✅ Published docker images to DockerHub
- ✅ Support for OpenId Connect using Keycloak

In terms of testing:

- ✅ Integration Tests (JVM and Native mode)
- ✅ Using Mocks
- ✅ Use of Architecture Unit Test
- ✅ Use of Wiremock for stubs
- ✅ Use of GatLing for Stress Tests that can run against different environments

Nice to have:

- ❌ Define custom metrics for the endpoints
- ❌ Resiliance4J for REST Clients
- ❌ Pagination
- ❌ Audit for tables
- ❌ Events and outbox pattern
- ❌ Use of BDD


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

You can create a native executable and creating a docker image using the following command.

```shell script
./gradlew clean build -Dquarkus.package.jar.enabled=false -Dquarkus.native.enabled=true
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./gradlew build -Dquarkus.package.jar.enabled=false -Dquarkus.native.enabled=true -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./build/quarkus-template-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/gradle-tooling>.

## Building docker image

it is quite easy, after you build the native executable you can build the docker image using:

```shell script
 docker build -f src/main/docker/Dockerfile.native-micro -t com.caelcs/quarkus-template .
```

## Running Native Tests

```shell
./gradlew clean testNative
```

## Running docker image prod for tests

In order to run the platform locally please install the following:

- Rancher Desktop or Minikube

After this you can run all the manifest to start the services

### Start services
```shell
cd src/main/kuberntes
kubectl apply -f postgres.yaml -f keycloak.yaml -f quarkus-template.yaml
```

Now once all the services are up and running you can run the following command to check the status of the services
```shell
kubectl port-forward svc/quarkus-app 8081:8080
kubectl port-forward svc/keycloak 8082:8080
kubectl port-forward svc/postgres 5432:5432
````

so now you can test keycloak by browsing to the following URL
```shell
http://localhost:8082/
```

The configuration that we have provided includes a client:

```
clientId: quarkus-template-app
clientSecret: YSFwvyazqPmLukTvwBWa0ZhlhtP3T031
```

it can redirect to any url.

it does includes a number of test users and roles.

```
username: alice
password: alice
role: user

username: alice
password: alice
role: user

username: john
password: john
role: admin

username: linda
password: linda
role: support

username: lucas
password: lucas
role: report

username: pinza
password: pinza
role: user, admin, support, report

```

Assuming you have created the user alice, you can generate an access token:

```shell
export access_token=$(\
    curl --insecure -X POST http://localhost:8081/realms/quarkus-template/protocol/openid-connect/token \
    --user quarkus-template-app:YSFwvyazqPmLukTvwBWa0ZhlhtP3T031 \
    -H 'content-type: application/x-www-form-urlencoded' \
    -d 'username=alice&password=alice&grant_type=password' | jq --raw-output '.access_token' \
 )
```

this will create an environment variable called access_token with the access token.

### Stop services
```shell
kubectl delete -f postgres.yaml -f keycloak.yaml -f quarkus-template.yaml
```

#### NOTE: Setup of Keycloak is done by importing the json file defined in the config map. That config map can ge generated from the keycloak admin.

## Export generating keycloak users and roles

Exporting is not a straight forward process. You have to create a script that will export the users and roles from keycloak.
The process export the realm but in a separate files the roles and users so then we have to merge them together.

```shell
First you can start postgres and keycloakby running:

```shell
kubectl apply -f postgres.yaml -f keycloak.yaml -f quarkus-template.yaml
```

Second. Login to keycloak and create the roles and users that you need but keep in mind that you will have to adjust the native test to make it work.

Third, get the pod id:


```shell
kubectl get pods
```


Forth, run the export process

```shell
kubectl exec -it keycloak-78cbfd58b8-87jz4 -- /opt/keycloak/bin/kc.sh export --dir /opt/keycloak/data/export --users realm_file
```

Fifth you can copy the files to your local machine if you can or do a cat so you copy the content.

```shell
kubectl exec -it keycloak-78cbfd58b8-87jz4 -- cat /opt/keycloak/data/export/quarkus-template-realm.json
```

Sixth, Copy the content and paste it in the resources/test-realm.json file in the native-test folder.

### Github Actions

There three CI pipelines available:

Pull Request: This pipeline is triggered when you create a pull request that is derived from main branch and the name start with feature/

Main: This pipeline is triggered when you push to the main branch or merge the pull request.

release: This pipeline is triggered when you create a release on the main branch. 
It will build the docker image and push it to DockerHub.

for this you have to create github tab that starts with v*

```shell
 git tag -a v1.0.15 -m "Release version 1.0.15"
 git push origin v1.0.15
```

this will kick the release build.

## Stress tests using gatling

the repo comes with a set of stress tests using gatling. there is source folder called gatlin/kubernetes where you can find all the manifests.

The way this works is write your scenarios and then build a docker image running from the root of the project.

```shell
docker build -f src/gatling/docker/Dockerfile -t adolfoecs/quarkus-template-gatling:1.0.0 .
```
then push the image to docker hub and update the gatling.yaml located in the kubernetes folder with the new image.

then you can start the tests by running all the pods:

```shell
kubectl apply -f src/gatling/kubernetes/postgres.yaml -f src/gatling/kubernetes/keycloak.yaml -f src/gatling/kubernetes/quarkus-template.yaml -f src/gatling/kubernetes/gatling.yaml
```

after that the test will start running.

You can run this by running the test against any environment by deploying the gatling pod.
