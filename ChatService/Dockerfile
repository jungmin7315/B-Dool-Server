FROM openjdk:17-jdk-alpine

ADD *.jar /app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]