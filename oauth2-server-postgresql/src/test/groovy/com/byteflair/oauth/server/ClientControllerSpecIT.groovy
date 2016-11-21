package com.byteflair.oauth.server

import groovy.util.logging.Slf4j
import io.restassured.http.ContentType
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.provider.client.BaseClientDetails
import org.springframework.test.context.TestPropertySource
import spock.lang.Shared
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
class ClientControllerSpecIT extends Specification {

    @LocalServerPort
    int port

    @Shared
    def accessToken;

    @Shared

            client = [
                    client_id             : 'test',
                    client_secret         : 'secret',
                    scope                 : 'trust',
                    authorized_grant_types: 'authorization_code,password,refresh_token,implicit,client_credentials',
                    authorities           : 'ROLE_TRUSTED_CLIENT',
                    access_token_validity : 900,
                    refresh_token_validity: 43200,
                    detail1               : 'detail1',
                    detail2               : 'detail2',
                    autoapprove           : 'true'
            ]

    String username = "mercury"

    String password = "secret"

    def "Authenticate"() {
        //given: "An existing client"
        when: "The client logs in"
        def response = given().accept(ContentType.JSON).contentType(ContentType.JSON)
                .auth().basic(username, password)
                .post("http://localhost:" + port + "/oauth/token?grant_type=client_credentials")
        def body = response.as(Map)
        accessToken = body.get('access_token')
        then: "The client obtains an access token"
        response.then().log().all()
                .statusCode(200)

    }

    def "That can create new client"() {
        //given: "An existing client"
        when: "Send POST request to create client"
        def response = given().accept(ContentType.JSON).contentType(ContentType.JSON)
                .auth().oauth2(accessToken)
                .body(client)
                .post("http://localhost:" + port + "/client")
        then: "The client is created correctly and returns"
        response.then().log().all()
                .statusCode(200)

        def body = response.as(BaseClientDetails)

        assert body.getClientId().equals(client['client_id'])
        assert body.getScope().equals(client['scope'].split(',').collect { it as String }.toSet())
        assert body.getAuthorizedGrantTypes().equals(client['authorized_grant_types'].split(',').collect {
            it as String
        }.toSet())
        assert body.getAuthorities().equals(client['authorities'].split(',').collect { it -> new SimpleGrantedAuthority(it) })
        assert body.getAccessTokenValiditySeconds().equals(client['access_token_validity'])
        assert body.getRefreshTokenValiditySeconds().equals(client['refresh_token_validity'])
        assert body.getAdditionalInformation() != null && !body.getAdditionalInformation().isEmpty()
        assert body.getAdditionalInformation().get("detail1").equals(client['detail1'])
        assert body.getAdditionalInformation().get("detail2").equals(client['detail2'])
    }

    def "That can't create client_id that already exists "() {
        //given: "An existing client"
        when: "Send POST request to create client"
        def response = given().accept(ContentType.JSON).contentType(ContentType.JSON)
                .auth().oauth2(accessToken)
                .body(client)
                .post("http://localhost:" + port + "/client")
        then: "The client can't be created"
        response.then().log().all()
                .statusCode(500)
    }

    def "That can return existing client"() {
        //given: "An existing client"
        when: "Send GET request "
        def response = given().accept(ContentType.JSON).contentType(ContentType.JSON)
                .auth().oauth2(accessToken)
                .get("http://localhost:" + port + "/client/" + client['client_id'])
        then: "The client is returned"
        response.then().log().all()
                .statusCode(200)

        def body = response.as(BaseClientDetails)

        assert body.getClientId().equals(client['client_id'])
        assert body.getScope().equals(client['scope'].split(',').collect { it as String }.toSet())
        assert body.getAuthorizedGrantTypes().equals(client['authorized_grant_types'].split(',').collect {
            it as String
        }.toSet())
        assert body.getAuthorities().equals(client['authorities'].split(',').collect { it -> new SimpleGrantedAuthority(it) })
        assert body.getAccessTokenValiditySeconds().equals(client['access_token_validity'])
        assert body.getRefreshTokenValiditySeconds().equals(client['refresh_token_validity'])
        assert body.getAdditionalInformation() != null && !body.getAdditionalInformation().isEmpty()
        assert body.getAdditionalInformation().get("detail1").equals(client['detail1'])
        assert body.getAdditionalInformation().get("detail2").equals(client['detail2'])

    }

    def "That can return list of clients"() {
        //given: "An existing client"
        when: "Send GET request "
        def response = given().accept(ContentType.JSON).contentType(ContentType.JSON)
                .auth().oauth2(accessToken)
                .get("http://localhost:" + port + "/client")
        then: "List of clients is returned"
        response.then().log().all()
                .statusCode(200)

        def body = response.as(List)

        assert !body.isEmpty()
    }

    def "That can't create client without client_id"() {
        given: "A client without client_id"
        def clientAux = client
        clientAux.client_id = null
        when: "Send POST request to create client"
        def response = given().accept(ContentType.JSON).contentType(ContentType.JSON)
                .auth().oauth2(accessToken)
                .body(clientAux)
                .post("http://localhost:" + port + "/client")
        then: "The client can't be created"
        response.then().log().all()
                .statusCode(500)
    }

    def "That can't create client without client_secret"() {
        given: "A client without client_secret"
        def clientAux = client
        clientAux.client_secret = null
        when: "Send POST request to create client"
        def response = given().accept(ContentType.JSON).contentType(ContentType.JSON)
                .auth().oauth2(accessToken)
                .body(clientAux)
                .post("http://localhost:" + port + "/client")
        then: "The client can't be created"
        response.then().log().all()
                .statusCode(500)
    }

    def "That can't create client without scope"() {
        given: "A client without scope"
        def clientAux = client
        clientAux.scope = ''
        when: "Send POST request to create client"
        def response = given().accept(ContentType.JSON).contentType(ContentType.JSON)
                .auth().oauth2(accessToken)
                .body(clientAux)
                .post("http://localhost:" + port + "/client")
        then: "The client can't be created"
        response.then().log().all()
                .statusCode(500)
    }
}
