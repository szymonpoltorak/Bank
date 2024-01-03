FROM gradle:8.2-jdk17-alpine AS build

WORKDIR /home/bank

COPY build.gradle .

COPY settings.gradle .

COPY src/ src/

RUN gradle build -x test

RUN mv build/libs/bank-backend-0.0.1.jar bank.jar

FROM alpine:latest

RUN apk update --no-cache

RUN apk add --no-cache openjdk17-jre

WORKDIR /bank

COPY --from=build /home/bank/bank.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "bank.jar"]
