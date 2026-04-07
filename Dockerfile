FROM eclipse-temurin:17-jdk
EXPOSE 8080
ADD target/springbootexample-app.jar springbootexample-app.jar
ENTRYPOINT ["java","-jar","/springbootexample-app.jar"]