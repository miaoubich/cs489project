You may use this skeleton as a starting point for your solution. It contains a simple
Spring Boot web application written using Java 17 and built using Gradle.

## Requirements

You need to have Java JDK 17 installed on your machine and available on your path.

## Instructions

The application is built using Gradle, via the included wrapper (`gradlew`).

- To build the application, use `gradlew clean build`
- Any tests that you add under the `tests` folder can be run using `gradlew clean test`
- To run the application, use `gradlew bootRun`

### Table of Contents ###
### Overview ###
* Technologies Used
* Project Structure
* Setup Instructions
* Testing
* API Endpoints
* Database Configuration

This is a fully functional Java Spring Boot application is designed to manage customers, videos, video copies, and rentals. It provides RESTful API endpoints for performing CRUD operations on these entities. The application uses a modular structure with separate modules for Customer , Video, VideoCopy, and Rental, each encapsulating related functionality. API authentication/authorization implementation - I've skip it since it's not optional.

### Technologies Used ###
* Java
* Spring Boot
* Spring Data JPA / Hibernate
* MySql Workbench, and MySQL Docker image
* JUnit 5 (for unit and integration testing)
* MockMvc (for testing MVC controllers)
* Gradle (for dependency management)
* IDE STS (Spring Tool Suite)
* Postman API
* Docker
* UML Diagram

### Project Structure ###
The project is organized into three modules:

* Customer Module: Manages customer information and operations, and handls rental transactions between customers and video copies..
* Video Module: Handles video information and operations.
* VideoCopy Module: Manages video copies and associated rentals.

### Setup Instructions ###
Prerequisites
Java Development Kit (JDK) installed
The latest version of STS (Spring Tool Suite) IDE which is built-in support for Gradle
IDE: STS (Spring Tool Suite) for development

### API Endpoints ###
### Customer API ###
* GET http://localhost:8080/video-rental-store/api/v1/customer: Retrieve all customers.
* GET http://localhost:8080/video-rental-store/api/v1/customer/{customerId}: Retrieve customer by ID.
* POST http://localhost:8080/video-rental-store/api/v1/customer: Create a new customer.
* PUT http://localhost:8080/video-rental-store/api/v1/customer/{customerId}: Update an existing customer.
* DELETE http://localhost:8080/video-rental-store/api/v1/customer/{customerId}: Delete a customer.
### Video API ###
* GET http://localhost:8080/video-rental-store/api/v1/videos: Retrieve all videos.
* GET http://localhost:8080/video-rental-store/api/v1/videos/{videoId}: Retrieve video by ID.
* POST http://localhost:8080/video-rental-store/api/v1/videos: Create a new video.
* PUT http://localhost:8080/video-rental-store/api/v1/videos/{videoId}: Update an existing video.
* DELETE http://localhost:8080/video-rental-store/api/v1/videos/{videoId}: Delete a video.
### VideoCopy API ###
* GET http://localhost:8080/video-rental-store/api/v1/videocopy: Retrieve all video copies.
* GET http://localhost:8080/video-rental-store/api/v1/videocopy/{videoCopyId}: Retrieve video copy by ID.
* POST http://localhost:8080/video-rental-store/api/v1/videocopy: Create a new video copy.
* PUT http://localhost:8080/video-rental-store/api/v1/videocopy/{copyId}: Update an existing video copy.
* DELETE http://localhost:8080/video-rental-store/api/v1/videocopy/{copyId}: Delete a video copy.
### Database Configuration ###
The application uses an MySQL database for local development and MySQL Docker image. Database configuration properties can be found in application.yml.