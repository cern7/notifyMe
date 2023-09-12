#
# Build stage
#
FROM maven:4.0.0-openjdk-18-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
WORKDIR /home/app
RUN mvn install -DskipTests

#
# Package stage
#

# Ubuntu based jdk 17 image
FROM openjdk:17-alpine
COPY --from=build /home/app/target/*.jar /usr/local/lib/my-app.jar
# Port used for connection
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/my-app.jar"]
