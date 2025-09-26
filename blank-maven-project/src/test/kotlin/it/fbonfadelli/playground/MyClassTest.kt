package it.fbonfadelli.playground

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MyClassTest {

    /*
    - read the user data from somewhere
    - check whether today it is the birthday of a user or not (with edge cases)
    - for the users which have their birthdays today, send the message
     */

    /*
    - mock - source of data
    - keep the logic
    - mock - message sender
    - mock - current date provider
    */

    /*
    - friends list is empty - no message are sent
    - friends list contain some friends - none is born today - no messages are sent
    - friends list contain some friends - one is born today - send message to specific user
    - friends list contain some friends - two are born today - send message to those two users
    */

    @Test
    fun `my first test`() {
        assertThat(true).isTrue
    }
}