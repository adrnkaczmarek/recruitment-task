FROM openjdk:10
COPY target/github-rest-service-1.0-SNAPSHOT.war .
CMD ["java", "-jar", "github-rest-service-1.0-SNAPSHOT.war"]
EXPOSE 8080