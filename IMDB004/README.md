IMDB database (film database)



Using Spring Boot, Gradle, Thymeleaf (front end generation) and JPA (Java persistence)

  instead of the JPA generated functions, a @Query is used here on table namebasics 
  in native mySQL format.
  Reason for this is the extensive filtering.

  beware: for a number of actors, there exists persons with the same name. 
  Therefore, the query for /actorinfo filters out persons without a birthdate as do the 
  /getcoincidence and the /getseparation endpoints when determining the actor or actors
  that serve as paramaters to the function.
  However, when assembling the list of persons involved as actor in a movie, this 
  filter is not applied. Instead, the person list is filtered on the category of the relationship
  with the movie.

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


- /getfilminfo

  Enter a film name. Film and crew of films with this name will appear
  

- /getseparation

  Enter two actor names. Degree of separation is calculated along with an example path
  between the two actors.


Next steps:
  code walk with Bruna
  /getseparation first actor is always Kevin Bacon, store sets of separation in memory
  use java JPQL from model instead of native queries in JPA
  use new Java constructs (lambda expressions, streams, collections) of versions 8 and later
  use regression tests
  adapt/add to the design document


 
  
