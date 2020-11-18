package org.johan.application.useCase.input

import org.johan.application.useCases.getQuizzes.GetQuizzesInput
import spock.lang.Specification

class GetQuizzesInputSpec extends Specification {

    def "should be invalid with customerId null"() {

        when:
        def input = new GetQuizzesInput(null)

        then:
        !input.getModelState().isValid()
    }

}
