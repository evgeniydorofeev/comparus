FROM eclipse-temurin:17-alpine
RUN mkdir /opt/app
COPY target/comparus.jar /opt/app
COPY init-db1.sql /
COPY init-db2.sql /
EXPOSE 8080
ENTRYPOINT ["java","-jar","/opt/app/comparus.jar"]
