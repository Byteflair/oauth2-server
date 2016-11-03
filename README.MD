# Maven

Most of the project, if not all, is configured around Maven. You can run de server, or set it up running Maven commands. 

# Profiles

Maven is configured to use the Maven profile with the same name as the Spring profile used. The following profiles are available:

 * release cycle profiles:
    * dev
    * int
    * uat
    * pro
 * platform profiles, orthogonal to release cycle 
    * mysql
    * postgresql

For example, if you want to execute the project in `dev` using `postgresql` you should execute the followng command:
 
```
 mvn -Pdev,postgresql -Dspring.profiles.active=dev,postgresql spring-boot:run
```

# Starting a database for development and testing

The Oauth server is ready to work with MySQL and PostgrSQL databases. If you run the server under the `dev` Spring profile, it will create all necessary database tables in your database of choice. To select a database you must set the `mysql` or `postgresql` Spring and Maven profiles.
 
If you have Docker installed in your system, you can start a Docker container with your database of choice using maven. To do so, you must select your preferred database using profile options and execute the `docker:run` goal. For example to run MySQL container:

```
mvn -Pdev,mysql -Dspring.profiles.active=dev,mysql docker:run
```

# Application porperties

You can override any application properties in the command line. For example, if you are running a postgres database in port `5433` instead of the default `5432` and its access credenitals are `postgres:postgres` you could override default values with the followiing command:
 
```
 mvn -Pdev,postgresql -Dspring.profiles.active=dev,postgresql -Dspring.datasource.username=postgres -Dspring.datasource.password=postgres -Dspring.datasource.port=5433 spring-boot:run
```

# Project setup

This OAuth2 Server implementation uses JWT tokens. These tokens are signed with an RSA key pair. Before running the server you need to generate an RSA key pair in a key store.

The application is by default configured to use a keystore in `${project.build.path}`.
 
You can generate an initialized key store for development and testing purposes using the following command: `mvn -Dspring.profiles.active=true keytool:generateKeyPair`

# Running the server

After running the project setup, you can run the server with the following command: `mvn -Dspring.profiles.active=true spring-boot:run`
 
 
