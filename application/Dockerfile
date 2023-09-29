#
# Build stage
#
FROM maven:3.6.3-openjdk-17 AS build
COPY application/src /home/app/src
COPY application/pom.xml /home/app
ARG jwt_secret
ARG jwt_accessTokenValidityMs
ARG jwt_cookieName
ARG mail_trap_token
ARG PGHOST
ARG PGPORT
ARG PGDATABASE
ARG PGUSER
ARG PGPASSWORD
ENV jwt.secret=$jwt_secret
ENV jwt.accessTokenValidityMS=$jwt_accessTokenValidityMs
ENV jwt.cookieName=$jwt_cookieName
ENV mailtrap.token=$mail_trap_token
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://$PGHOST:$PGPORT/$PGDATABASE
ENV SPRING_DATASOURCE_USERNAME:$PGUSER
ENV SPRING_DATASOURCE_PASSWORD:$PGPASSWORD
ENV SPRING_DATASOURCE_DRIVER-CLASS-NAME=org.postgresql.Driver
WORKDIR /home/app
RUN mvn install


#
# Package stage
#

# Ubuntu based jdk 17 image
FROM openjdk:17-alpine
COPY --from=build /home/app/target/*.jar /usr/local/lib/my-app.jar
# Port used for connection
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/my-app.jar"]
