package org.johan.webApi.useCase.v1

import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.annotation.MicronautTest
import org.johan.infrastructure.dataAccess.entities.CustomerEntity
import org.johan.webApi.useCases.v1.signUp.models.SignUpRequest
import spock.lang.Specification
import spock.lang.Unroll

import javax.inject.Inject
import javax.persistence.EntityManager

@MicronautTest
class SignUpControllerSpec extends Specification {

    @Inject
    EntityManager entityManager

    @Inject
    ServerClient client

    def setup() {
        entityManager.persist(CustomerEntity.builder()
                .email("emailExist@email.com")
                .password("azeqsd")
                .build())
    }

    def "should signUp correctly"() {
        when:
        def response = client.signup(new SignUpRequest("email@email.com", "azeqsd"))

        then:
        response.getStatus() == HttpStatus.CREATED
        response.getBody().isPresent()
        def body = response.getBody().get()
        body.email == "email@email.com"
    }

    @Unroll
    def "should throw a bad request with #email and #password"() {

        when:
        client.signup(new SignUpRequest(email, password))

        then:
        def error = thrown(HttpClientResponseException)
        error.message == 'Error on created customer'

        where:
        email             | password
        'email@email.com' | null
        null              | 'azeqsd'
        null              | null
    }

    def "should throw a bad request when customer already exist"() {
        when:
        client.signup(new SignUpRequest('emailExist@email.com', 'password'))

        then:
        def error = thrown(HttpClientResponseException)
        error.message == 'Customer already exist'
    }

}
