# Slideshow

To get started:

1. Run `mvnw clean package` to build an application image.
2. Run `docker-compose up -d` in the project root folder to run the application.

The app is listening on port 8080. You can test the API by sending the following example requests:

7. curl -X GET http://localhost:8080/slideShow/1/proof-of-play/1

addImage and addSlideShow operations return the created entity ID which can be used in subsequent requests.

In this version Hibernate is configured to re-create DB schema after each restart, so all the data created in the previous session is lost.

http://localhost:8080/swagger-ui/index.html

