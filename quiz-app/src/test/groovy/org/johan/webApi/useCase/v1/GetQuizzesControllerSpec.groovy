package org.johan.webApi.useCase.v1

import io.micronaut.http.HttpStatus
import io.micronaut.test.annotation.MicronautTest
import org.johan.infrastructure.dataAccess.entities.CustomerEntity
import org.johan.infrastructure.dataAccess.entities.QuestionEntity
import org.johan.infrastructure.dataAccess.entities.QuizEntity

import spock.lang.Specification

import javax.inject.Inject
import javax.persistence.EntityManager

@MicronautTest
class GetQuizzesControllerSpec extends Specification {
    @Inject
    ServerClient client

    @Inject
    EntityManager entityManager

    def setup() {
        CustomerEntity customer = CustomerEntity.builder()
                .email("email@email.com")
                .password("azeqsd")
                .build()
        entityManager.persist(customer)

        entityManager.persist(CustomerEntity.builder()
                .email("email2@email.com")
                .password("azeqsd")
                .build())

        QuizEntity quiz = QuizEntity.builder()
                .completed(true)
                .timeFinished(4566)
                .customer(customer)
                .build()
        entityManager.persist(quiz)

        entityManager.persist(QuestionEntity.builder()
                .category("Entertainment: Television")
                .correctAnswer("Screech")
                .difficulty("easy")
                .question("Which character was played by Dustin Diamond in the sitcom &#039;Saved by the Bell&#039;?")
                .incorrectAnswer(["Zack", "Mr. Belding", "A.C. Slater"] as Set)
                .status("ANSWERED_CORRECTLY")
                .quiz(quiz)
                .build())
        entityManager.persist(QuestionEntity.builder()
                .category("Entertainment: Television")
                .correctAnswer("10")
                .difficulty("easy")
                .question("How many seasons did &quot;Stargate SG-1&quot; have?")
                .incorrectAnswer(["12", "3", "7"] as Set)
                .status("ANSWERED_CORRECTLY")
                .quiz(quiz)
                .build())
        entityManager.persist(QuestionEntity.builder()
                .category("Entertainment: Television")
                .correctAnswer("Petyr Baelish")
                .difficulty("easy")
                .question("In Game of Thrones, what is Littlefinger&#039;s real name?")
                .incorrectAnswer(["Zack", "Mr. Belding", "A.C. Slater"] as Set)
                .status("ANSWERED_CORRECTLY")
                .quiz(quiz)
                .build())
        entityManager.persist(QuestionEntity.builder()
                .category("Entertainment: Television")
                .correctAnswer("2013")
                .difficulty("easy")
                .question("When did the TV show Rick and Morty first air on Adult Swim?")
                .incorrectAnswer(["2003", "2017", "2011"] as Set)
                .status("ANSWERED_WRONGLY")
                .quiz(quiz)
                .build())
    }

    def "should get quiz for customer 1"() {
        when:
        def response = client.getQuizzes(1L)

        then:
        response.getStatus() == HttpStatus.OK
        response.getBody().isPresent()
        def body = response.getBody().get()
        body.quizzes.size == 1
        body.quizzes.get(0).name == "Quiz 1"
    }

    def "should get no content response for customer 2"() {
        when:
        def response = client.getQuizzes(2L)

        then:
        response.getStatus() == HttpStatus.NO_CONTENT
    }

    def "should throw not found with invalid customer"() {
        when:
        def response = client.getQuizzes(999L)

        then:
        response.getStatus() == HttpStatus.NOT_FOUND
    }
}
