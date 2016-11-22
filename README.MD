# Maven

Most of the project, if not all, is configured around Maven. You can run the server or set it up running Maven commands. 

# Profiles

Maven is configured to use the Maven profile with the same name as the Spring profile used. The following profiles are available:

 * release cycle profiles:
    * dev
    * int
    * uat
    * pro

For example, if you want to execute the project in `dev` using `postgresql` you should execute the following command:
 
```
 mvn -Pdev -Dspring.profiles.active=dev spring-boot:run
```
in the `oauth2-server-postgresql` root directory

# Starting a database for development and testing

The Oauth server is ready to work with MySQL and PostgreSQL databases. If you run the server under the `dev` Spring profile, it will create all necessary database tables in your database of choice. To select a database you must launch one of the projects (`oauth2-server-mysql` or `oauth2-server-postgresql`).
 
If you have Docker installed in your system, you can start a Docker container with your database of choice using maven. To do so, you must select your preferred database using profile options and execute the `docker:run` goal. For example to run MySQL container:

```
mvn -Pdev -Dspring.profiles.active=dev docker:run
```

in the `oauth2-mysql-server` root directory

# Application properties

You can override any application properties in the command line. For example, if you are running a PostgreSQL database in port `5433` instead of the default `5432` and its access credentials are `postgres:postgres` you could override default values with the followiing command:
 
```
 mvn -Pdev -Dspring.profiles.active=dev -Dspring.datasource.username=postgres -Dspring.datasource.password=postgres -Dspring.datasource.port=5433 spring-boot:run
```

Property `spring.datasource.initialize` initialize (or not) schema and example data of the database when running the server
This property value is `false` by default excluding `dev` profile, where it's `true`. Be aware of it when using in production environments.


# Project setup

This OAuth2 Server implementation uses JWT tokens. These tokens are signed with an RSA key pair. Before running the server you need to generate an RSA key pair in a key store.

The application is by default configured to use a keystore in `${project.build.path}`.
 
You can generate an initialized key store for development and testing purposes using the following command: `mvn -Dspring.profiles.active=true keytool:generateKeyPair`

# Running the server

After running the project setup, you can run the server with the following command: `mvn -Pdev -Dspring.profiles.active=true spring-boot:run`
 
 
