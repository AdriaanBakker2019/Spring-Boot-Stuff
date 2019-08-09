Dogservice 002 is a web application:
    • depending on the environment variable MYENV connects either 
      to a Postgress database (postgres_demo) with user postgresuser and password password (MYENV=prod)
      or to a mySQL database in a docker container (IMDB) with user demouser and password passwordi (MYENV=dev)
 
    • Maven has been used to build. Spring CLI has been uses to set up the project. The mySQL dependency in the POM.xml has been added later
      manually. 
    • To create the project from the command line with spring CLI:
      See
      https://www.callicoder.com/spring-boot-jpa-hibernate-postgresql-restful-crud-api-example/
    • spring init --name=postgres-demo --dependencies=web,jpa,postgresql postgres-demo
      

Service endpoints:
/
    shows index page
/add_dog
    shows page to add a dog
/list
    lists the dog
/delete
    shows page to delete a dog


See https://spring.io/guides/gs/rest-service/
See https://dzone.com/articles/spring-boot-with-spring-data-jpa
to be able to understand this first read
https://www.amitph.com/spring-boot-rest-service/#2_Mock_Data_Provider


