# Wine Library
![](https://raw.githubusercontent.com/YaroslavRadevychVynnytskyi/content-wine-library/main/backend-readme-pics/main-pic-1.jpeg)

## 1. About the project
This repository contains a source code for the backend of the "Wine Library" project,
which is a REST API. The main aim of the “Wine Library” is to make internet wine 
buying as satisfying as possible. To achieve this, we did our best to provide elegant design and advanced features 
alongside reliability and robustness. 
A key feature of the Wine Library is its AI-driven recommendation system. This innovative 
functionality allows us to provide personalized wine suggestions based on users' 
preferences. Join us on a journey to explore the world of wines with Wine Library, 
where technology meets elegance to deliver an exceptional shopping experience.\
Feel free to visit and explore our site by [this link](https://bodyarespect.github.io/wine-library/)!

## 2. Where to find front-end part of the project
You can check out the front-end part of our project [here](https://github.com/BodyaRespect/wine-library).

## 3. Documentation and usage
Use this url to find information about the API:
https://api.winelibrary.wuaze.com/swagger-ui/index.html#/  
Also, you may try using Postman to send requests. [Here](https://web.postman.co/workspace/bbc70790-d1bb-4376-989e-8b0901791111) 
is public collection of all available endpoints.

## 4. How to run the project on your machine
#### Requirements:
* JDK 17.0.10
* Apache Maven version 3.8.7 or higher
* MySQL Server 8 or MariaDB 11.2.3
#### Instruction:
1. Fork and clone this repository ```git clone git@github.com:your-username/wine-library.git```
2. Move to ```wine-library/src/main/resources``` directory and modify 
   ```application.properties``` with your db configuration settings and API keys.
3. Create a scheme in your RDMS by typing ```CREATE DATABASE `wine_library_db`; ```
4. Build the project by ```mvn clean package```
5. Start the project by moving to the ```target``` directory
and running ``` java -jar wine-library-0.0.1-SNAPSHOT.jar```

If you did everything correctly, you may access swagger by following URL:
http://localhost:8080/swagger-ui.html#/

## 5. Technologies used

* Development, persistence and build tools
1. JDK 17.0.10
2. Spring Boot 3.2.5 -> Spring MVC, Spring Data JPA, Spring Security 
3. MariaDB 11.2.4
4. Apache Maven 3.9.7
5. Liquibase 4.24.0

* SDKs
1. Stripe API 25.9.0 for checkout
2. Twilio SDK 8.24.0 for phone verification of order
3. Google OAuth2 1.2.0 for quick login

* Third-party APIs
1. DeepSeek API for AI-based wine-recommendation feature
2. GeoNames API for retrieving geo data

* Libraries
1. OkHttp 5.0.0
2. Jackson 2.15.14
3. JJWT 0.11.5
4. Mapstruct 1.5.5
5. Mockito 5.7.0
6. JUnit 4.13.2
7. Lombok 1.18.32

* Deployment
1. Docker 26.1.4
2. AWS (EC2, RDS)
