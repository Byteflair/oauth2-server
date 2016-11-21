package com.byteflair.oauth.server

import groovy.util.logging.Slf4j
import io.restassured.http.ContentType
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification
import spock.lang.Stepwise

import static io.restassured.RestAssured.given

/**
 * Created by calata on 21/11/16.
 */
@SpringBootTest(classes = ITConfig.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = ['spring.profiles.active=dev',
        'spring.jpa.database=POSTGRESQL',
        'spring.jpa.show-sql=true',
        'spring.jpa.generate-ddl=false',
        'spring.datasource.initialize=true',
        'spring.datasource.host=localhost',
        'spring.datasource.port=5432',
        'spring.datasource.dbname=oauth_db',
        'spring.datasource.username=oauth_server',
        'spring.datasource.password=password',
        'spring.datasource.platform=postgresql',
        'spring.datasource.url=jdbc:postgresql://${spring.datasource.host}:${spring.datasource.port}/${spring.datasource.dbname}?autoReconnect=true&useUnicode=true&characterEncoding=utf8&noAccessToProcedureBodies=true&protocol=tcp',
        'spring.datasource.driver-class-name=org.postgresql.Driver',
        'spring.data.rest.returnBodyOnCreate=true',
        'spring.data.rest.returnBodyOnupdate=true',
        'server.contextPath=/',
        'keystore.path=target/keystore.jks',
        'keystore.password=password',
        'keystore.key.alias=dev_oauth_jwt_key',
        'keystore.key.password=password',
        'logging.config=classpath:logback-development.xml',
        'logging.level.com.gpsauriga.sso=DEBUG',
        'logging.level.org.springframework.security=DEBUG'])
@Slf4j
@Stepwise
class PasswordFlowSpecIT extends Specification {
    @LocalServerPort
    int port

    String username = "admin"
    String password = "secret"
    String client_id = "mercury"
    String client_secret = "secret"

    def "That can authenticate user"() {
        when: "The user logs in"
        def response = given().accept(ContentType.JSON).contentType(ContentType.JSON)
                .auth().basic(client_id, client_secret)
                .post("http://localhost:" + port + "/oauth/token?grant_type=password&username=" + username + "&password=" + password)
        then: "The user obtains an access token"
        response.then().log().all()
                .statusCode(200)

        def body = response.as(Map)
        assert body.get('access_token') != null
        assert body.get('email') != null
        assert body.get('details') != null

    }

    def "That can authenticate client with x-www-form-urlencoded"() {
        //given: "An existing client"
        when: "The client logs in"
        def response = given().accept(ContentType.JSON).contentType(ContentType.URLENC)
                .auth().basic(client_id, client_secret)
                .body("grant_type=password&scope=trust&username=" + username + "&password=" + password)
                .post("http://localhost:" + port + "/oauth/token?grant_type=password")
        then: "The client obtains an access token"
        response.then().log().all()
                .statusCode(200)

        def body = response.as(Map)
        assert body.get('access_token') != null
        assert body.get('email') != null
        assert body.get('details') != null

    }

    def "That can not authenticate unexisting client"() {
        given: "Invalid credentials"
        def username = "not_found"
        def password = "whatever"
        when: "The cllient logs in"
        def response = given().accept(ContentType.JSON).contentType(ContentType.JSON)
                .auth().basic(client_id, client_secret)
                .post("http://localhost:" + port + "/oauth/token?grant_type=password&username=" + username + "&password=" + password)
        then: "Access denied"
        response.then().log().all()
                .statusCode(400)
    }

    def "That can not authenticate invalid credentials"() {
        given: "Invalid credentials"
        def password = "whatever"
        when: "The cllient logs in"
        def response = given().accept(ContentType.JSON).contentType(ContentType.JSON)
                .auth().basic(client_id, client_secret)
                .post("http://localhost:" + port + "/oauth/token?grant_type=password&username=" + username + "&password=" + password)
        then: "The client obtains an access token"
        response.then().log().all()
                .statusCode(400)
    }
}
