# testukas

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/testukas-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Provided Code

### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)

### login credentials
user1 password1
user2 password123

## curl snippets
# user1 login

curl -X POST http://localhost:8080/auth/login \
-H "Content-Type: application/json" \
-d '{"username": "user1", "password": "password1"}'


# user2 login

curl -X POST http://localhost:8080/auth/login \
-H "Content-Type: application/json" \
-d '{"username": "user2", "password": "password123"}'

# user1 buy 2 for 400
curl -X POST http://localhost:8080/orders \
-H "Content-Type: application/json" \
-H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwOi8vbG9jYWxob3N0IiwidXBuIjoidXNlcjEiLCJzdWIiOiJ1c2VyMSIsImdyb3VwcyI6WyJVU0VSIl0sInVzZXJJZCI6MSwiZnVsbE5hbWUiOiJVc2VyIE9uZSIsImlhdCI6MTc1Mjg3MTIwMCwiZXhwIjoxNzUyOTU3NjAwLCJqdGkiOiJkMjFjY2U2Zi00MTNiLTQzYWMtODUyOC04MTNjYjIyZTM4YTAifQ.cmapLaoLB-m9Ko_6Kb6u9HZuIebtJpiGMTrvJDqtlDOvrziFTGrly8so11VVWyT4kU3WEy-QNFCLEGe7Ktx8J4R5JsOQoRrGvyxZFzLGCfnVC4p0yPouGRLjl263OUE0yidZAiZeKy-5G7gSbZoirmnog3fPQHXLkPW5fJA9nZ6HlvEoJrnXqKI6NnMukJjKYFVztpnffC002mHPrhWa8m29IUu7WtHOfstLbdJloUx9NAllMVDC2WfCY5eh8hmkxA98ceo6H5uLLZoXIVvMilUlNeY4z46a1TeLCoPm8b38xe43ZQkW7MmQQVucXTrvUBDsuI-0x4eM0BeyHPV6PQ" \
-d '{"item": "Laptop", "quantity": 2, "type":"BUY", "price": "400"}'

# user2 sell 2 for 400
curl -X POST http://localhost:8080/orders \
-H "Content-Type: application/json" \
-H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwOi8vbG9jYWxob3N0IiwidXBuIjoidXNlcjIiLCJzdWIiOiJ1c2VyMiIsImdyb3VwcyI6WyJVU0VSIl0sInVzZXJJZCI6MiwiZnVsbE5hbWUiOiJUZXN0IFVzZXIgMiIsImlhdCI6MTc1Mjg0MzIxMCwiZXhwIjoxNzUyOTI5NjEwLCJqdGkiOiI3OWE1ZjM2OS01ZjY2LTQwZmQtYmY0OS05MmEwZWUxYTM2OGYifQ.IXYufNq2vLbYxfAVy_AJk-86lgOwNC0ToQV6DtXrnEofyc1zqM8eu0VZspuUcS0Zlumt8qEoOtM-2csmFlfTJBRFujYzLkWqMTCaAFWQg81EE-bAm87FUih_SBj6x1X81yoD62dYYNaVjEd7GZi1ZUto9L_fPWIRrjbMNJU-WPQHr-EuBvCoICSFUd76zDE8m3_xNGWDjqe0oaXAZTC2qgPqP-fGCx8kMTzXfEgAHhTiXrIvzcqUEQPT5vVAMzp7awHM-2r4aSNXOIddoAp3cOKADC0MM4n9DfL_hgy_ykQcSrHZAgBb18JcDvG2kY0kZhJzWprw1KwoyUus_NYBww" \
-d '{"item": "Laptop", "quantity": 2, "type":"SELL", "price": "400"}'


### Known problems:
V2__add_two_users migration does not populate users table