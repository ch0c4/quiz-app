package org.johan.webApi.useCase.v1


import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.annotation.MicronautTest
import org.johan.infrastructure.dataAccess.entities.CustomerEntity
import org.johan.webApi.useCases.v1.login.models.LoginRequest
import spock.lang.Specification
import spock.lang.Unroll

import javax.inject.Inject
import javax.persistence.EntityManager

@MicronautTest
class LoginControllerSpec extends Specification {

    @Inject
    EntityManager entityManager

    @Inject
    ServerClient client;

    def setup() {
        entityManager.persist(CustomerEntity.builder()
                .email("email@email.com")
                .password("0d34d250688c15039e892b7cea22e045")
                .build())
    }

    def "should login correctly"() {
        when:
        def response = client.login(new LoginRequest("email@email.com", "azeqsd"))

        then:
        response.getStatus() == HttpStatus.OK
        response.getBody().isPresent()
        def body = response.getBody().get()
        body.id == 1
        body.email == "email@email.com"
    }

    def "should throw not found on bad email"() {
        when:
        def response = client.login(new LoginRequest("gbeohbao@email.com", "azeqsd"))

        then:
        response.getStatus() == HttpStatus.NOT_FOUND
    }

    def "should throw bad request exception on bad password"() {
        when:
        client.login(new LoginRequest("email@email.com", "bad password"))

        then:
        def error = thrown(HttpClientResponseException)
        error.message == 'Password is invalid'
    }

    @Unroll
    def "should throw bad request with #email and #password"() {
        when:
        client.login(new LoginRequest(email, password))

        then:
        def error = thrown(HttpClientResponseException)
        error.message == 'Error on login'

        where:
        email             | password
        null              | 'azeqsd'
        'email@email.com' | null
        null              | null
    }
}
