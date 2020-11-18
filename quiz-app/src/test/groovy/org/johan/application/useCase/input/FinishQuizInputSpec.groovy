package org.johan.application.useCase.input

import org.johan.application.useCases.finishQuiz.FinishQuizInput
import spock.lang.Specification

class FinishQuizInputSpec extends Specification {

    def "should be invalid with quizId null"() {

        when:
        def input = new FinishQuizInput(null)

        then:
        !input.getModelState().isValid()
    }
}
