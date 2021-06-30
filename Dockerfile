FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY report-rest/target/*.jar app.jar
EXPOSE 8913
ENTRYPOINT ["java","-jar","/app.jar"]