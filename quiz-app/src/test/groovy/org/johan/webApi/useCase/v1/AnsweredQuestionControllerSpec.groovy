package org.johan.webApi.useCase.v1

import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.annotation.MicronautTest
import org.johan.infrastructure.dataAccess.entities.CustomerEntity
import org.johan.infrastructure.dataAccess.entities.QuestionEntity
import org.johan.infrastructure.dataAccess.entities.QuizEntity
import org.johan.webApi.useCases.v1.answeredQuestion.models.AnsweredQuestionRequest
import spock.lang.Specification
import spock.lang.Unroll

import javax.inject.Inject
import javax.persistence.EntityManager
import javax.transaction.Transactional

@MicronautTest
class AnsweredQuestionControllerSpec extends Specification {

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
                .completed(false)
                .timeFinished(0)
                .customer(customer)
                .build()
        entityManager.persist(quiz)


        entityManager.persist(QuestionEntity.builder()
                .category("Entertainment: Television")
                .correctAnswer("Screech")
                .difficulty("easy")
                .question("Which character was played by Dustin Diamond in the sitcom &#039;Saved by the Bell&#039;?")
                .incorrectAnswer(["Zack", "Mr. Belding", "A.C. Slater"] as Set)
                .status("NOT_ANSWERED")
                .quiz(quiz)
                .build())

        entityManager.persist(QuestionEntity.builder()
                .category("Entertainment: Television")
                .correctAnswer("10")
                .difficulty("easy")
                .question("How many seasons did &quot;Stargate SG-1&quot; have?")
                .incorrectAnswer(["12", "3", "7"] as Set)
                .status("NOT_ANSWERED")
                .quiz(quiz)
                .build())

        entityManager.persist(QuestionEntity.builder()
                .category("Entertainment: Television")
                .correctAnswer("Petyr Baelish")
                .difficulty("easy")
                .question("In Game of Thrones, what is Littlefinger&#039;s real name?")
                .incorrectAnswer(["Zack", "Mr. Belding", "A.C. Slater"] as Set)
                .status("NOT_ANSWERED")
                .quiz(quiz)
                .build())

        entityManager.persist(QuestionEntity.builder()
                .category("Entertainment: Television")
                .correctAnswer("2013")
                .difficulty("easy")
                .question("When did the TV show Rick and Morty first air on Adult Swim?")
                .incorrectAnswer(["2003", "2017", "2011"] as Set)
                .status("NOT_ANSWERED")
                .quiz(quiz)
                .build())
    }

    @Unroll
    void "should answer question #status"() {

        when:
        def response = client.answerQuestion(1, 1, 1, body)

        then:
        response.getStatus() == HttpStatus.OK
        response.getBody().isPresent()
        response.getBody().get().completed == false

        where:
        body                                        | status
        new AnsweredQuestionRequest("Screech", 123) | 'ANSWERED_CORRECTLY'
        new AnsweredQuestionRequest("toto", 123)    | 'ANSWERED_WRONGLY'

    }

    @Unroll
    def "should send a bad request exception #text"() {
        when:
        client.answerQuestion(1, 1, 1, body)

        then:
        def error = thrown(HttpClientResponseException)
        error.message == 'Error on answering question'

        where:
        body                                      | text
        new AnsweredQuestionRequest("toto", null) | 'no answerTime'
        new AnsweredQuestionRequest(null, 123)    | 'no answer'
        new AnsweredQuestionRequest(null, null)   | 'no answer and no answerTime'
    }

    @Unroll
    def "should send a not found exception #text"() {

        when:
        def response = client.answerQuestion(questionId, quizId, customerId, body)

        then:
        response.getStatus() == HttpStatus.NOT_FOUND

        where:
        body                                     | questionId | quizId | customerId | text
        new AnsweredQuestionRequest("toto", 123) | 999        | 1      | 1          | "Question not found"
        new AnsweredQuestionRequest("toto", 123) | 1          | 999    | 1          | "Quiz not found"
        new AnsweredQuestionRequest("toto", 123) | 1          | 1      | 999        | "Customer not found"
    }

    def "should complete quiz"() {
        given:
        completeQuiz()

        when:
        def response = client.answerQuestion(4, 1, 1, body as AnsweredQuestionRequest)

        then:
        response.getStatus() == HttpStatus.OK
        response.getBody().isPresent()
        response.getBody().get().completed

        where:
        body << new AnsweredQuestionRequest("2013", 12345)

    }

    @Transactional
    def completeQuiz() {
        def transaction = entityManager.getTransaction()

        println "=========> COMPLETE QUIZ <==========="
        def question = entityManager.find(QuestionEntity.class, 1L)
        question.setStatus("ANSWERED_CORRECTLY")
        entityManager.persist(question)

        question = entityManager.find(QuestionEntity.class, 2L)
        question.setStatus("ANSWERED_CORRECTLY")
        entityManager.persist(question)

        question = entityManager.find(QuestionEntity.class, 3L)
        question.setStatus("ANSWERED_CORRECTLY")
        entityManager.persist(question)

        question = entityManager.find(QuestionEntity.class, 4L)
        question.setStatus("ANSWERED_CORRECTLY")
        entityManager.persist(question)

        transaction.commit()
    }
}
