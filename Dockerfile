# Builder stage
FROM openjdk:17-jdk as builder
WORKDIR application
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

# Final stage
FROM openjdk:17-jdk
WORKDIR application
COPY src/main/resources/keystore.p12 src/main/resources/keystore.p12
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
EXPOSE 443

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
