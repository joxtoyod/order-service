# syntax=docker/dockerfile:1
FROM amazoncorretto:21.0.10-al2023 AS build
RUN dnf install -y findutils && dnf clean all
WORKDIR /workspace
COPY gradlew settings.gradle.kts build.gradle.kts gradle.properties ./
COPY gradle gradle
RUN ./gradlew --no-daemon dependencies > /dev/null
COPY src src
RUN ./gradlew --no-daemon bootJar -x test

FROM amazoncorretto:21.0.10-al2023
WORKDIR /app
COPY --from=build /workspace/build/libs/*.jar app.jar
ARG APP_COMMIT=unknown
ENV APP_COMMIT=${APP_COMMIT}
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
