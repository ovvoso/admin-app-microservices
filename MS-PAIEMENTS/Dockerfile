FROM openjdk:17-ea-17-jdk-slim-buster

LABEL maintainer="Thierno Sow"

COPY target/paiement-ms.jar paiement-ms.jar

ENTRYPOINT ["java","-jar","paiement-ms.jar"]

EXPOSE 8080
