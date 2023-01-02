#Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim as build

#Information around who maintains the image
MAINTAINER gaurav

# Add the application's jar to the container
COPY target/notification-service-0.0.1-SNAPSHOT.jar notification-service-0.0.1-SNAPSHOT.jar

#execute the application
ENTRYPOINT ["java","-jar","/notification-service-0.0.1-SNAPSHOT.jar"]