#FROM openjdk:12.0.2

#EXPOSE 8080

#ADD ./build/libs/*.jar app.jar

#ENTRYPOINT ["java","-jar","/app.jar"]

FROM gcr.io/distroless/java:11-nonroot

ADD ./build/libs/*.jar app.jar

EXPOSE 8080

CMD ["app.jar"]
