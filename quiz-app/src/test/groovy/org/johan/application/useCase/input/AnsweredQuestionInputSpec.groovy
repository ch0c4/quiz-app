package org.johan.application.useCase.input

import org.johan.application.useCases.answeredQuestion.AnsweredQuestionInput
import spock.lang.Specification
import spock.lang.Unroll

class AnsweredQuestionInputSpec extends Specification {

    @Unroll
    def "should be invalid with param #customerId , #quizId , #questionId"() {

        when:
        def input = new AnsweredQuestionInput(customerId, quizId, questionId, "toto", 123L)

        then:
        !input.getModelState().isValid()

        where:
        customerId | quizId | questionId
        null       | 1L     | 1L
        1L         | null   | 1L
        1L         | 1L     | null
    }

}
