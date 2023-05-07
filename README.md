# Doliver Finance
This project is for a personal study. The main goals is experimental the follow technologies:

- Spring Boot
- Docker
- Relational databases
- No-relational databases
- Messages and Event Streaming platforms

### How to run
Follow the steps

1. Build the project
```
./gradlew clean build
```
2. Execute Flyway Migration

TODO: need to find a solution that works with Java 16

3. Run application
   1. Command line
      ```
      java -jar build/finance-api-{version}.jar
      ```
   2. Docker
      ```
      docker-compose up -d
      ```
