IMDB database (film database)

Using Spring Boot, Gradle, Thymeleaf (front end generation) and JPA (Java persistence)

  instead of the JPA generated functions, a @Query is used here on table namebasics 
  in native mySQL format.
  Reason for this is the extensive filtering.

Service endpoints:

- Find actor and films he was in (GET from url string) 
  actorinfo?<actor name>
  example:
  http://localhost:8080/actorinfo?name=Frank%20Sinatra

- Find actor and films he was in (enter actor name on form, POST)
  getactorinfo
  http://localhost:8080/getactorinfo

- Find film info by film name
  getfilminfo

- Find coincident films for two actors
  getcoincidence

  Next steps:
  use java JPQL from model instead of native queries in JPA


 
  
