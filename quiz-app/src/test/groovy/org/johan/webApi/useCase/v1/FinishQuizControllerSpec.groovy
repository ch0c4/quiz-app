package org.johan.webApi.useCase.v1


import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.annotation.MicronautTest
import org.johan.infrastructure.dataAccess.entities.CustomerEntity
import org.johan.infrastructure.dataAccess.entities.QuestionEntity
import org.johan.infrastructure.dataAccess.entities.QuizEntity
import spock.lang.Specification

import javax.inject.Inject
import javax.persistence.EntityManager

@MicronautTest
class FinishQuizControllerSpec extends Specification {

    @Inject
    EntityManager entityManager

    @Inject
    ServerClient client

    def setup() {
        CustomerEntity customer = CustomerEntity.builder()
                .email("email@email.com")
                .password("azeqsd")
                .build()
        entityManager.persist(customer)

        QuizEntity quiz = QuizEntity.builder()
                .completed(true)
                .timeFinished(4566)
                .customer(customer)
                .build()
        entityManager.persist(quiz)

        entityManager.persist(QuizEntity.builder()
                .completed(false)
                .timeFinished(4566)
                .customer(customer)
                .build())

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


    def "should get the finished quiz"() {

        when:
        def response = client.finishQuiz(1)

        then:
        response.getStatus() == HttpStatus.OK
        response.getBody().isPresent()
        def body = response.getBody().get()
        body.totalCorrectAnswer == 3
        body.timeFinish == 4566
        body.questions.size == 4
    }

    def "should not found quiz"() {
        when:
        def response = client.finishQuiz(999)

        then:
        response.getStatus() == HttpStatus.NOT_FOUND
    }

    def "should throw bad request on quiz not completed"() {

        when:
        client.finishQuiz(2)

        then:
        def error = thrown(HttpClientResponseException)
        error.message == 'Quiz not completed'
    }
}
