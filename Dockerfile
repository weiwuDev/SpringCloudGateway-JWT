FROM openjdk:8-jdk-alpine
ADD target/*.jar SpringCloudGateway-0.0.1-SNAPSHOT.jar
EXPOSE 8080 8443
ENTRYPOINT ["java", "-jar", "SpringCloudGateway-0.0.1-SNAPSHOT.jar"]