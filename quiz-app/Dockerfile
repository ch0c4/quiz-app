FROM openjdk:14-alpine
COPY build/libs/quiz-app-*-all.jar quiz-app.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "quiz-app.jar"]