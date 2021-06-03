FROM openjdk:15
ARG JAR_FILE=taobao-0.1.2.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]