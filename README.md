# Quarkus Template

This project uses **[Quarkus](https://quarkus.io/)** — the Supersonic Subatomic Java Framework.

The goal of this template is to provide engineers with a quick and practical reference for the most common enterprise use cases when building microservices with Quarkus.

---

## ✅ Features

### Application Features

- **REST API** exposure
- **Database integration** with connection pooling
- **Bean declarations** via configuration classes
- **Micrometer metrics** endpoint exposure
- **Health checks** for readiness and liveness
- **REST Clients** using connection pools
- **Swagger/OpenAPI** generation and exposure
- **Hexagonal architecture**
- **YAML configuration**
- **Config-to-class binding**
- **MapStruct integration**
- **Structured logging** with JSON for PROD
- **MDC** correlation ID generation and propagation to REST clients
- **Centralized error handling**
- **Dependency version monitoring**
- **CI/CD with GitHub Actions**
    - Pull request builds
    - Main branch builds
    - Release builds
- **Native binary generation** and attachment to GitHub releases
- **Docker image publishing** to Docker Hub
- **OpenID Connect** support via Keycloak

### Testing Support

- Integration tests (JVM and Native mode)
- Mocking
- Architecture unit tests
- WireMock for stubbing
- Gatling-based stress testing (targeting different environments)

### ✨ Nice to Have (WIP)

- ❌ Custom metrics per endpoint
- ❌ Resilience4J for REST clients
- ❌ Pagination support
- ❌ Audit logging for tables
- ❌ Event sourcing and outbox pattern
- ❌ BDD-style tests

---

## 🚀 Getting Started

### Run in Dev Mode

```bash
./gradlew quarkusDev
```

Access Dev UI: [http://localhost:8080/q/dev](http://localhost:8080/q/dev)

---

## 🧱 Build and Package

### JVM Mode

```bash
./gradlew build
```

Run the app:

```bash
java -jar build/quarkus-app/quarkus-run.jar
```

### Uber Jar

```bash
./gradlew build -Dquarkus.package.jar.type=uber-jar
```

---

## 🧊 Native Executable

### Local Build (Requires GraalVM)

```bash
./gradlew clean build -Dquarkus.package.jar.enabled=false -Dquarkus.native.enabled=true
```

### Container Build (No GraalVM Required)

```bash
./gradlew build -Dquarkus.native.container-build=true
```

---

## 🐳 Docker Image

After building the native executable:

```bash
docker build -f src/main/docker/Dockerfile.native-micro -t com.caelcs/quarkus-template .
```

---

## 🧪 Run Native Tests

```bash
./gradlew clean testNative
```

---

## 🧰 Local Testing with Docker

### Requirements

- Rancher Desktop or Minikube

### Deploy Services

```bash
cd src/gatling/kubernetes
kubectl apply -f postgres.yaml -f keycloak.yaml -f quarkus-template.yaml
```

### Port Forwarding

```bash
kubectl port-forward svc/quarkus-app 8081:8080
kubectl port-forward svc/keycloak 8082:8080
kubectl port-forward svc/postgres 5432:5432
```

Access Keycloak: [http://localhost:8082](http://localhost:8082)

### Pre-configured Keycloak Client

```
clientId: quarkus-template-app
clientSecret: YSFwvyazqPmLukTvwBWa0ZhlhtP3T031
```

### Test Users

| Username | Password | Roles                  |
|----------|----------|------------------------|
| alice    | alice    | user                   |
| john     | john     | admin                  |
| linda    | linda    | support                |
| lucas    | lucas    | report                 |
| pinza    | pinza    | user, admin, support, report |

### Get Access Token (Example)

```bash
export access_token=$(curl -X POST http://localhost:8082/realms/quarkus-template/protocol/openid-connect/token \
--user quarkus-template-app:YSFwvyazqPmLukTvwBWa0ZhlhtP3T031 \
-H 'Content-Type: application/x-www-form-urlencoded' \
-d 'username=alice&password=alice&grant_type=password' | jq -r '.access_token')
```

---

## 🛑 Stop Services

```bash
kubectl delete -f postgres.yaml -f keycloak.yaml -f quarkus-template.yaml
```

---

## 🔄 Export Keycloak Users & Roles

```bash
kubectl apply -f postgres.yaml -f keycloak.yaml
kubectl get pods
kubectl exec -it <keycloak-pod-id> -- /opt/keycloak/bin/kc.sh export --dir /opt/keycloak/data/export --users realm_file
kubectl exec -it <keycloak-pod-id> -- cat /opt/keycloak/data/export/quarkus-template-realm.json
```

Paste the realm JSON into `resources/test-realm.json`.

---

## ✅ GitHub Actions

### Pipelines

- **Pull Request**: Triggered on `feature/*` targeting `main`
- **Main Branch**: On push to `main`
- **Release**: On GitHub Release tagged as `v*`

```bash
git tag -a v1.0.15 -m "Release version 1.0.15"
git push origin v1.0.15
```

---

## 📈 Stress Testing with Gatling

### Prerequisites

- Rancher Desktop or Minikube
- Prometheus Operator installed

```bash
helm install prometheus-operator prometheus-community/kube-prometheus-stack
```

### Build & Push Gatling Image

```bash
docker build -f src/gatling/docker/Dockerfile -t adolfoecs/quarkus-template-gatling:1.0.0 .
```

Update the image in `src/gatling/kubernetes/gatling.yaml` and apply:

```bash
kubectl apply -f src/gatling/kubernetes/postgres.yaml \
              -f src/gatling/kubernetes/keycloak.yaml \
              -f src/gatling/kubernetes/quarkus-template.yaml \
              -f src/gatling/kubernetes/gatling.yaml
```