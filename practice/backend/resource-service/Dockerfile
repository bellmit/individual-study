FROM openjdk:8
MAINTAINER gyw
ADD resource-service-1.0.0.jar resource-service.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","resource-service.jar","--spring.profiles.active=local"]