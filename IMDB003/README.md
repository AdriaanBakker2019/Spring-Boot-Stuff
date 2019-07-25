IMDB database (film database)

Using Spring Boot, Gradle, Thymeleaf (front end generation) and JPA (Java persistence)


Service endpoints:

- Find actor 
  list?<actor name>
  example:
  http://localhost:8080/list?name=Frank%20Sinatra

  instead of the JPA generated functions, a @Query is used here on table namebasics 
  in native mySQL format.
  Reason for this is the extensive filtering.
