# CRUD application template

## Constituents and possibilities

1. Spring Boot Web application
2. API docs (Swagger UI)
3. Database migration (Liquibase)
4. Unit-testing (Groovy Spock)
5. Blackbox-testing (Testcontainers + Rest Assured)

## Local setup and run

1. Install and launch docker. 
2. Install IntelliJ IDEA, open the project and wait for completion of dependencies download.
3. Run `src/test/groovy/root/LocalDevApplicationRunner.groovy`
4. Open API docs: `http://localhost:8080/swagger-ui.html`