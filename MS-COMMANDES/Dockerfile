FROM openjdk:17-ea-17-jdk-slim-buster

LABEL maintainer="Thierno Sow"

COPY target/commande-ms.jar commande-ms.jar

ENTRYPOINT ["java","-jar","commande-ms.jar"]

EXPOSE 8080
