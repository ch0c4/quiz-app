package org.johan.application.useCase.input

import org.johan.application.useCases.startQuiz.StartQuizInput
import spock.lang.Specification

class StartQuizInputSpec extends Specification {

    def "should be invalid with customerId null"() {

        when:
        def input = new StartQuizInput(null, "category", "easy")

        then:
        !input.getModelState().isValid()
    }
}
