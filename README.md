# Getting Started

### The upload-service is available with:

* Docker Compose - [http://localhost:8080]()
* Swagger - [http://localhost:8080/swagger-ui.html]()
* Jacoco - [/target/site/jacoco/index.html]()

### To run on the docker, run the following commands:

* `mvn clean install`
* `docker-compose up --build` 

### If you want to run outside the Docker, you need a mongodb running. Here's an example of how to create mongo in Docker:
* `docker run --name upload-db -d -e MONGO_INITDB_ROOT_USERNAME=root -e MONGO_INITDB_ROOT_PASSWORD=secret -p 27017:27017 mongo` 

