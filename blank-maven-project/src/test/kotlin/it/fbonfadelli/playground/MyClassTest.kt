package it.fbonfadelli.playground

import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.time.LocalDate

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

    private val friendsLoader = mockk<FriendsLoader>()
    private val greetingSender = mockk<GreetingSender>()
    private val birthDayGreetings = BirthDayGreetings(friendsLoader, greetingSender)


    @Test
    fun `friend list is empty`() {
        every { friendsLoader.getAll() } returns emptyList()

        birthDayGreetings.execute()

        verify { greetingSender wasNot Called }
    }
}

class BirthDayGreetings(
    friendsLoader: FriendsLoader,
    greetingSender: GreetingSender
) {
    fun execute() {
    }
}

interface FriendsLoader {
    fun getAll(): List<Friend>
}

class Friend(
    val firstName: String,
    val lastName: String,
    val dateOfBirth: LocalDate,
    val email: String,
)

interface GreetingSender
