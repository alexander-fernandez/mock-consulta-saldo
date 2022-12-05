FROM registry.access.redhat.com/ubi9/openjdk-17-runtime:latest

# Refer to Maven build -> finalName
ARG JAR_FILE=target/mock-consulta-saldo-1.0.0-SNAPSHOT.jar

# cd /deployments
WORKDIR /deployments

# cp target/spring-boot-web.jar /deployments/app.jar
COPY ${JAR_FILE} app.jar

# Expose ports
EXPOSE 8080 8778 9779

# java -jar /deployments/app.jar
ENTRYPOINT ["java","-jar","/deployments/app.jar"]