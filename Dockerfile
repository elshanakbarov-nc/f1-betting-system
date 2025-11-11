# ---------- 1) BUILD STAGE ----------
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

COPY gradlew gradlew
COPY gradle gradle
RUN chmod +x gradlew

COPY build.gradle .
COPY settings.gradle .

COPY src src

RUN ./gradlew --no-daemon clean bootJar -x test


FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/build/libs/*.jar /app/app.jar

ENV JAVA_OPTS=""

# Expose the Spring Boot default port

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
