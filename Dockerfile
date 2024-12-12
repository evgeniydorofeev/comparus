FROM eclipse-temurin:17-alpine
RUN mkdir /opt/app
COPY target/comparus.jar /opt/app
COPY init.sql /
EXPOSE 8080
ENTRYPOINT ["java","-jar","/opt/app/comparus.jar"]
