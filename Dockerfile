FROM openjdk:19-jdk-alpine
COPY target/users-api-services-0.0.1-SNAPSHOT.jar users-api-services-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/users-api-services-0.0.1-SNAPSHOT.jar"]