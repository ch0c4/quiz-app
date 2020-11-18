package org.johan.application.services

import spock.lang.Specification

class NotificationSpec extends Specification {

    Notification notification

    def setup() {
        notification = new Notification()
    }

    def "should add an notification with key already defined"() {
        given:
        def key = 'Test'
        notification.add(key, "Message with Test Key")

        when:
        notification.add(key, "The second message")

        then:
        notification.getErrorMessages().get(key).size() == 2
    }
}
