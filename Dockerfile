FROM eclipse-temurin:17-jdk
EXPOSE 8080
ADD target/springbootexample-app.war springbootexample-app.war
ENTRYPOINT ["java","-war","/springbootexample-app.war"]