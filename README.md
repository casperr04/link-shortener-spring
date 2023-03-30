Link shortener API, created with Spring Boot. Features entity expiration removal, adjustable with a cron schelude.



## Technologies used
Backend - Spring Boot

Database - PostgreSQL

Testing - JUnit 5, Mockito

Other - Project Lombok, Springdoc, Jackson


## Getting Started
Link shortener is a Spring Boot application built using Maven.

Firstly, you will need to set up PostgreSQL, which can be downloaded from here.
https://www.postgresql.org/download/ 

* Create a database created link-shortener
* In /src/main/resources, change the application.properties:
```
...
spring.datasource.url=jdbc:postgresql://localhost:5432/link-shortener
...
spring.datasource.username=[YOUR USERNAME]
spring.datasource.password=[YOUR PASSWORD]
...
```

* You can also create a seperate application.properties file and set those fields with the packaged jar.


## Running the API
You can run the application directly with
```
mvnw spring-boot:run
```
Or by packaging it and running it as a jar.
```
mvnw package
java -jar link-shortener-1.0.0.jar
```

## Endpoints
By default, documentation on the endpoints will be available on /swagger-ui/index.html.
* You can disable this in application properties with ``` springdoc.swagger-ui.enabled=false```

The API is pretty simple, with two endpoints. The first one will let you create a redirect URL, given an original link, on ```/link``` 
```
{
  "link": "examplelink.com"
}
```

If the URL is valid, you will get a response, like this:
```
{
  "link": "localhost8080/liAfG452k",
  "expirationDate": "2023-03-29T18:59:11.541Z"
}
```

The link will automatically redirect to the original URL, although it will be removed after the expiration date, which can be configured.

## Configuration
You can configure the expiration date of each link in application.properties.
```
link_expiry_days=[DAYS]
link_expiry_hours=[HOURS]
link_expiry_minutes=[MINUTES]
```
The application follows a schelude, in which it will remove all expired links.

By default, it is every 30 minutes, although this can be changed in the application.properties, using cron. 

Here is an example, with a scheluded removal every 30 minutes.
```
cron_expiration_date=0 0/30 * * * ?
```

## Running Tests

To run tests, run the following command

```
  mvnw test
```


## Issues and feedback
Given this is my first Spring project, it is very likely that there are issues.

Feel free to point those out in the githubs issue tracker, or contribute with a pull request.
## License

[MIT](https://choosealicense.com/licenses/mit/)

