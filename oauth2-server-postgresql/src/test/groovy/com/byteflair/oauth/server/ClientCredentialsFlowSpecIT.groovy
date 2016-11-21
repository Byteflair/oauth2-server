/*
 * Copyright (c) 2016 Byteflair
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
 * Created by Daniel Cerecedo <daniel.cerecedo@byteflair.com> on 31/10/16.
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
class ClientCredentialsFlowSpecIT extends Specification {

    @LocalServerPort
    int port

    String username = "mercury"

    String password = "secret"

    def "That can authenticate client"() {
        //given: "An existing client"
        when: "The cllient logs in"
        def response = given().accept(ContentType.JSON).contentType(ContentType.JSON)
                .auth().basic(username, password)
                .post("http://localhost:" + port + "/oauth/token?grant_type=client_credentials")
        then: "The client obtains an access token"
        response.then().log().all()
                .statusCode(200)

        def body = response.as(Map)
        assert body.get('access_token') != null

    }

    def "That can authenticate client with x-www-form-urlencoded"() {
        //given: "An existing client"
        when: "The client logs in"
        def response = given().accept(ContentType.JSON).contentType(ContentType.URLENC)
                .body("grant_type=client_credentials&scope=trust&client_id=" + username + "&client_secret=" + password)
                .post("http://localhost:" + port + "/oauth/token?grant_type=client_credentials")
        then: "The client obtains an access token"
        response.then().log().all()
                .statusCode(200)

        def body = response.as(Map)
        assert body.get('access_token') != null

    }

    def "That can not authenticate unexisting client"() {
        given: "Invalid credentials"
        def username = "not_found"
        def password = "whatever"
        when: "The cllient logs in"
        def response = given().accept(ContentType.JSON).contentType(ContentType.JSON)
                .auth().basic(username, password)
                .post("http://localhost:" + port + "/oauth/token?grant_type=client_credentials")
        then: "Access denied"
        response.then().log().all()
                .statusCode(401)
    }

    def "That can not authenticate invalid credentials"() {
        given: "Invalid credentials"
        def password = "whatever"
        when: "The cllient logs in"
        def response = given().accept(ContentType.JSON).contentType(ContentType.JSON)
                .auth().basic(username, password)
                .post("http://localhost:" + port + "/oauth/token?grant_type=client_credentials")
        then: "The client obtains an access token"
        response.then().log().all()
                .statusCode(401)
    }
}
