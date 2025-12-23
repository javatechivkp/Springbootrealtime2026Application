FROM openjdk:17
EXPOSE 8080
ADD target/springbootexample-app.war springbootexample-app.war
ENTRYPOINT ["java","-war","/springbootexample-app.war"]