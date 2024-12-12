# Slideshow

To get started:

1. Run `mvnw clean package` to build an application image.
2. Run `docker-compose up -d` in the project root folder to run the application.

The app is listening on port 8080. 

You can test the API by sending the following example requests:

  curl -X GET http://localhost:8080/users

Access the OpenAPI documentation at the following URL:

http://localhost:8080/v3/api-docs

Access the Swagger UI at:

http://localhost:8080/swagger-ui/index.html
