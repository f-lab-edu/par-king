FROM openjdk:21
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENV TZ=Asia/Seoul
ENTRYPOINT ["java", "-Dspring.profiles.active=local,persistence-local", "-jar", "app.jar"]