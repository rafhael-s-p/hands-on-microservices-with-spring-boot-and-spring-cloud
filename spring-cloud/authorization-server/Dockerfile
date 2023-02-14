#FROM openjdk:12.0.2

#EXPOSE 9999

#ADD ./build/libs/*.jar app.jar

#ENTRYPOINT ["java","-jar","/app.jar"]

FROM gcr.io/distroless/java:11-nonroot

ADD ./build/libs/*.jar app.jar

EXPOSE 9999

CMD ["app.jar"]
