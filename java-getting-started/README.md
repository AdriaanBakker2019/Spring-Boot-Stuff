Java getting started - Heroku example, expanded to operate on separate databases in multiple environments:
Adriaan Bakker - wed 14 august, 2019

1. Development: Local on the MAC, using a Postgres database
2. Production: As a web application on Heroku, using a Postgres database

There are two environment variables:

1. MYENV - value can be "prod" or "dev". In case of "prod", the production environment is chosen.
   The application.properties file contains the line
   spring.profiles.active=${MYENV}
   thus either the application-dev.properties file is active (setting environment for development database) or 
   the application-prod.properties file is active (production database).
   
2. ENERGY string - used in the /hello endpoint. In production this environment variable has a different value.


Three endpoints:
/    shows a information html page
/hello   shows a mathematical equation 
/db      adds one time tick to a tick table and shows the ticks

To find access to the right tick table and in order to obtain the user/password/database combinations and find out 
about the right port to use please refer bto the two application-*.property files.


There are two small scripts to start the demo:

-To start the web page in development: ./start_dev.sh
-To start in production: ./start_prod.sh

