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

- find films by actor
  For now: actor key as identifier
  getfilm?ncode=<actor key>
  example:
  http://localhost:8080/getfilm?ncode=nm0000004

  Next steps:
  the actor and films listing will be combined to one endpoint
  only films that are really films
  use java JPQL from model instead of native queries in JPA


 
  
