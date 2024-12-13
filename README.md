# Comparus webapp

To get started:

1. Run `mvnw clean package` to build an application image.
2. Run `docker-compose up` in the project root folder to run the application.

Docker-compose will run two instances of PosgreSQL. On the first run it will initialize 
the databases with the following scripts:

The first instance:

`create table users (user_id text, login text, first_name text, last_name text);`<br/>
`insert into users values ('1', 'user-1', 'John', 'Doe');`

The second instance:

`create table users (user_id text, login text, first_name text, last_name text);`<br/>
`insert into users values ('2', 'user-2', 'Jane', 'Doe');`

The application will be running on port 8080. You can test the API by sending the following request from command line:

`curl -X GET http://localhost:8080/users`

You can filter the data by adding params to url as `<field>=<value>` pairs:

`curl -X GET http://localhost:8080/users?id=1`

OpenAPI documentation is at `http://localhost:8080/v3/api-docs`.<br/>
Swagger UI is at `http://localhost:8080/swagger-ui/index.html`.
