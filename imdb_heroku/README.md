this is a IMDB database 

Objective: should demonstrate a healthy development street for a typical modern Java webapplication:

 - example using Spring Boot and JPA
 - Use of Thymeleaf
 - Use of a database in Docker image
 - Use of logging framework
 - Use of regression tests
 - Use of microservice architecture that is apt for this application 
 - Use of GIT repository
 - Use of the hosting of a Heroku server with a database (preferrably in a docker image)

There are two configurations: (tbd, for now just local development within mySQL in a docker image. At this point in time,
the focus is on the architecture and the logging facility.

1. development - works with local mySQL database
2. production - works with remote on a Postgres database on a Heroku server


Microservice architecture
The idea of the application architecture is to create microservices, to have an architecture that splits the functionality (services) up into areas of concern.
Currently, there are four:
   Actors perspective
   Movies perspective
   Coincidence - builds on the Actors and Film perspective
   Degree of Separation - builds on the connecting dataset between actors and movies


Current endpoints:
  /getmessage - just a small test to get Thymeleaf going
  /getactorinfo - enter a name, the actor is shown
