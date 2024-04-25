FROM openjdk:17
EXPOSE 8080
ADD target/cs489-project.jar cs489-project.jar
ENTRYPOINT [ "java","-jar","/cs489-project.jar"]