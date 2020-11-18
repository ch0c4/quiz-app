package org.johan.webApi.useCase.v1

import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.annotation.MicronautTest
import io.micronaut.test.annotation.MockBean
import org.johan.infrastructure.dataAccess.entities.CustomerEntity
import org.johan.infrastructure.quizExchange.IQuizClient
import org.johan.infrastructure.quizExchange.QuizPackage
import org.johan.infrastructure.quizExchange.QuizResult
import org.johan.webApi.useCases.v1.startQuiz.models.StartQuizRequest
import spock.lang.Specification
import spock.lang.Unroll

import javax.inject.Inject
import javax.persistence.EntityManager

@MicronautTest
class StartQuizControllerSpec extends Specification {

    @Inject
    EntityManager entityManager

    @Inject
    ServerClient client

    @Inject
    IQuizClient quizClient

    def setup() {
        entityManager.persist(CustomerEntity.builder()
                .email("email@email.com")
                .password("azeqsd")
                .build())
    }

    def "should start quiz"() {
        when:
        def response = client.startQuiz(1L, new StartQuizRequest("TELEVISION", "EASY"))

        then:
        1 * quizClient.fetchQuiz('14', 'easy') >> getQuizClientResponseMock()
        response.getStatus() == HttpStatus.OK
        response.getBody().isPresent()

    }

    @Unroll
    def "should throw bad request with #category and #difficulty"() {
        when:
        client.startQuiz(1L, new StartQuizRequest(category, difficulty))

        then:
        def error = thrown(HttpClientResponseException)
        error.message == 'Error on start quiz'


        where:
        category     | difficulty
        'TELEVISION' | null
        null         | 'EASY'
        null         | null
    }

    def "should throw not found on unknown customer"() {
        when:
        def response = client.startQuiz(999L, new StartQuizRequest("TELEVISION", "EASY"))

        then:
        response.getStatus() == HttpStatus.NOT_FOUND
    }

    @Unroll
    def "should throw service unavailable on error client with #message"() {
        when:
        client.startQuiz(1L, new StartQuizRequest("TELEVISION", "EASY"))

        then:
        1 * quizClient.fetchQuiz('14', 'easy') >> responseClient
        def error = thrown(HttpClientResponseException)
        error.message == 'Quiz client unavailable'

        where:
        responseClient             | message
        HttpResponse.ok(null)      | 'response null'
        HttpResponse.serverError() | 'client error'

    }

    static def getQuizClientResponseMock() {
        List<QuizResult> quizResults = new ArrayList<>()
        quizResults.add(QuizResult.builder()
                .category("Science: Mathematics")
                .type("multiple")
                .difficulty("easy")
                .question("How many zeros are there in a googol?")
                .correct_answer("100")
                .incorrect_answers(["10", "1,000", "1,000,000"])
                .build())

        quizResults.add(QuizResult.builder()
                .category("Entertainment: Film")
                .type("multiple")
                .difficulty("easy")
                .question("Who directed the 2015 movie &quot;The Revenant&quot;?")
                .correct_answer("Alejandro G. I&ntilde;&aacute;rritu")
                .incorrect_answers(["Christopher Nolan", "David Fincher", "Wes Anderson"])
                .build())

        return HttpResponse.ok(QuizPackage.builder()
                .response_code(0)
                .results(quizResults)
                .build())

    }

    @MockBean(IQuizClient)
    IQuizClient quizClient() {
        Mock(IQuizClient)
    }

}
