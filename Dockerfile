FROM openjdk:17-jdk-slim

COPY scripts/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

COPY target/security-app-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["/wait-for-it.sh", "security-db:1433", "--","java","-jar","app.jar","--spring.profiles.active=docker"]