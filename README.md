# Takeaway Coding Challenge

Coding Challenge for Takeaway.com. The goal is to implement two services: employee­service and event­service.

## Getting Started

### Prerequisites

* git
* docker
* docker-compose

4 GB of RAM is recommended

### Installation

All should get up and run via

```
docker-compose up -d
```

## Usage

### API

Swagger API docs is available at

#### Employee Service 

<http://localhost:8080/api-docs>

UI at <http://localhost:8080/swagger-ui.html>

#### Event Service 

<http://localhost:8081/api-docs>

UI at <http://localhost:8081/swagger-ui.html>

## Testing

JDK 8 required on the host machine

Within the service directory run:

```
./gradlew test -i 
```

## Built With

* Spring
* Spring Boot
* RabbitMQ
* MySQL
* Apache Cassandra

## Architecture

## License