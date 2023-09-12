#
# Build stage
#
FROM maven:3.6.3-openjdk-17 AS build
COPY application/src /home/app/src
COPY application/pom.xml /home/app
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
