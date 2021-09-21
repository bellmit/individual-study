FROM openjdk:8
MAINTAINER gyw
ADD system-service-1.0.0.jar system-service.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","system-service.jar","--spring.profiles.active=remote"]