package it.fbonfadelli.playground

import io.mockk.*
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
    - friends list is empty - no message are sent [x]
    - friends list contain one friend - that friend is born today - the message is sent [x]
    - friends list contain one friend - that friend is not born today - the message is sent [x]
    - friends list contain one friend - that friend's birthday is today - the message is sent [x]

    - friends list contain some friends - none is born today - no messages are sent
    - friends list contain some friends - one is born today - send message to specific user
    - friends list contain some friends - two are born today - send message to those two users
    */

    private val currentDateProvider = mockk<CurrentDateProvider>()
    private val friendsLoader = mockk<FriendsLoader>()
    private val greetingSender = mockk<GreetingSender>()
    private val birthDayGreetings = BirthDayGreetings(friendsLoader, greetingSender, currentDateProvider)


    @Test
    fun `friend list is empty`() {
        every { friendsLoader.getAll() } returns emptyList()

        birthDayGreetings.execute()

        verify { greetingSender wasNot Called }
    }

    @Test
    fun `friends list contain one friend who is born today`() {
        val friend = Friend("::first_name::", "::last_name::", LocalDate.of(2000, 10, 20), "::an_email::")

        every { friendsLoader.getAll() } returns listOf(friend)
        every { currentDateProvider.get() } returns LocalDate.of(2000, 10, 20)
        justRun { greetingSender.sendGreetingsTo(friend) }

        birthDayGreetings.execute()

        verify { greetingSender.sendGreetingsTo(friend) }
    }

    @Test
    fun `friends list contain one friend whose birthday is not today`() {
        val friend = Friend("::first_name::", "::last_name::", LocalDate.of(2000, 10, 20), "::an_email::")

        every { friendsLoader.getAll() } returns listOf(friend)
        every { currentDateProvider.get() } returns LocalDate.of(2025, 10, 29)

        birthDayGreetings.execute()

        verify { greetingSender wasNot Called }
    }

    @Test
    fun `friends list contain one friend whose birthday is today but was not born today`() {
        val friend = Friend("::first_name::", "::last_name::", LocalDate.of(2000, 10, 20), "::an_email::")

        every { friendsLoader.getAll() } returns listOf(friend)
        every { currentDateProvider.get() } returns LocalDate.of(2025, 10, 20)
        justRun { greetingSender.sendGreetingsTo(friend) }

        birthDayGreetings.execute()

        verify { greetingSender.sendGreetingsTo(friend) }
    }
}

class BirthDayGreetings(
    private val friendsLoader: FriendsLoader,
    private val greetingSender: GreetingSender,
    private val currentDateProvider: CurrentDateProvider
) {
    fun execute() {
        val friends = friendsLoader.getAll()

        if (friends.isNotEmpty()) {

            val friend = friends.first()

            if (isBirthDay(friend))
                greetingSender.sendGreetingsTo(friend)
        }
    }

    private fun isBirthDay(friend: Friend): Boolean {
        val currentDate = currentDateProvider.get()
        val dateOfBirth = friend.dateOfBirth

        return dateOfBirth.month == currentDate.month && dateOfBirth.dayOfMonth == currentDate.dayOfMonth
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

interface CurrentDateProvider {
    fun get(): LocalDate
}

interface GreetingSender {
    fun sendGreetingsTo(friend: Friend)
}
