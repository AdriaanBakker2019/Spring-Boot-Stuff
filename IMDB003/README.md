IMDB database (film database)

Using Spring Boot, Gradle, Thymeleaf (front end generation) and JPA (Java persistence)

  instead of the JPA generated functions, a @Query is used here on table namebasics 
  in native mySQL format.
  Reason for this is the extensive filtering.

Service endpoints:

- /actorinfo?name=<a name>

  Find actor and films he was in (GET from url string) 
  example:
  http://localhost:8080/actorinfo?name=Frank%20Sinatra

- /getactorinfo

  Find actor and film he was in (enter actor name on form, POST)
  getactorinfo
  There is an option field that allows for the search of actors who have birthyear null
  (still to be changed to check box, tbd)

- /getcoincidence

  Enter two actor names: Find movies and tvshows shared by those two actors



  Next steps:
  use java JPQL from model instead of native queries in JPA
  add degrees of separation functionality
  use new Java constructs (lambda expressions, streams, collections) of versions 8 and later
  use regression tests
  adapt/add to the design document


 
  
