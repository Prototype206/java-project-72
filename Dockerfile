
FROM eclipse-temurin:21-jdk AS build
WORKDIR /work
COPY . .

RUN cd app && ./gradlew --no-daemon clean shadowJar

FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY --from=build /work/app/build/libs/app.jar app.jar
CMD ["java", "-jar", "app.jar"]
