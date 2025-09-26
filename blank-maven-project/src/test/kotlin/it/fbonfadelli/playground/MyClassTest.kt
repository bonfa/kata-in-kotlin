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
    - additional checks on the date - same month, different day - not birthday [x]
    - additional checks on the date - different month, same day - not birthday [x]

    - friends list contain some friends - none is born today - no messages are sent [x]
    - friends list contain some friends - one is born today - send message to specific user [x]
    - friends list contain some friends - two are born today - send message to those two users [x]
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
    fun `friends contain one friend who is born today`() {
        val friend = aFriendWith(dateOfBirth = LocalDate.of(2000, 10, 20))

        every { friendsLoader.getAll() } returns listOf(friend)
        every { currentDateProvider.get() } returns LocalDate.of(2000, 10, 20)
        justRun { greetingSender.sendGreetingsTo(friend) }

        birthDayGreetings.execute()

        verify { greetingSender.sendGreetingsTo(friend) }
    }

    @Test
    fun `friends contain one friend whose birthday is not today`() {
        val friend = aFriendWith(dateOfBirth = LocalDate.of(2000, 10, 20))

        every { friendsLoader.getAll() } returns listOf(friend)
        every { currentDateProvider.get() } returns LocalDate.of(2025, 9, 29)

        birthDayGreetings.execute()

        verify { greetingSender wasNot Called }
    }

    @Test
    fun `friends contain one friend whose birthday is today but was not born today`() {
        val friend = aFriendWith(dateOfBirth = LocalDate.of(2000, 10, 20))

        every { friendsLoader.getAll() } returns listOf(friend)
        every { currentDateProvider.get() } returns LocalDate.of(2025, 10, 20)
        justRun { greetingSender.sendGreetingsTo(friend) }

        birthDayGreetings.execute()

        verify { greetingSender.sendGreetingsTo(friend) }
    }

    @Test
    fun `additional checks on the date - same month, different day - not birthday`() {
        val friend = aFriendWith(dateOfBirth = LocalDate.of(2000, 10, 20))

        every { friendsLoader.getAll() } returns listOf(friend)
        every { currentDateProvider.get() } returns LocalDate.of(2025, 10, 21)

        birthDayGreetings.execute()

        verify { greetingSender wasNot Called }
    }

    @Test
    fun `additional checks on the date - different month, same day - not birthday`() {
        val friend = aFriendWith(dateOfBirth = LocalDate.of(2000, 10, 20))

        every { friendsLoader.getAll() } returns listOf(friend)
        every { currentDateProvider.get() } returns LocalDate.of(2025, 9, 20)

        birthDayGreetings.execute()

        verify { greetingSender wasNot Called }
    }

    @Test
    fun `friends contain more than one friend, one has birthday today`() {
        val friend1 = aFriendWith(dateOfBirth = LocalDate.of(2000, 10, 20))
        val friend2 = aFriendWith(dateOfBirth = LocalDate.of(2001, 3, 4))
        val friend3 = aFriendWith(dateOfBirth = LocalDate.of(2000, 6, 19))

        every { friendsLoader.getAll() } returns listOf(friend1, friend2, friend3)
        every { currentDateProvider.get() } returns LocalDate.of(2025, 6, 19)
        justRun { greetingSender.sendGreetingsTo(friend3) }

        birthDayGreetings.execute()

        verify { greetingSender.sendGreetingsTo(friend3) }
    }

    @Test
    fun `friends contain more than one friend, but no one has birthday today`() {
        val friend1 = aFriendWith(dateOfBirth = LocalDate.of(2000, 10, 20))
        val friend2 = aFriendWith(dateOfBirth = LocalDate.of(2001, 3, 4))
        val friend3 = aFriendWith(dateOfBirth = LocalDate.of(2000, 6, 19))

        every { friendsLoader.getAll() } returns listOf(friend1, friend2, friend3)
        every { currentDateProvider.get() } returns LocalDate.of(2025, 9, 20)

        birthDayGreetings.execute()

        verify { greetingSender wasNot Called }
    }

    @Test
    fun `friends contain more than one friend, more than one friend has birthday today`() {
        val friend1 = aFriendWith(dateOfBirth = LocalDate.of(2000, 10, 20))
        val friend2 = aFriendWith(dateOfBirth = LocalDate.of(2001, 6, 19))
        val friend3 = aFriendWith(dateOfBirth = LocalDate.of(2000, 6, 19))

        every { friendsLoader.getAll() } returns listOf(friend1, friend2, friend3)
        every { currentDateProvider.get() } returns LocalDate.of(2025, 6, 19)
        justRun { greetingSender.sendGreetingsTo(friend2) }
        justRun { greetingSender.sendGreetingsTo(friend3) }

        birthDayGreetings.execute()

        verify { greetingSender.sendGreetingsTo(friend2) }
        verify { greetingSender.sendGreetingsTo(friend3) }
    }

    private fun aFriendWith(dateOfBirth: LocalDate): Friend =
        Friend(
            firstName = "::first_name::",
            lastName = "::last_name::",
            dateOfBirth = dateOfBirth,
            email = "::an_email::"
        )
}

class BirthDayGreetings(
    private val friendsLoader: FriendsLoader,
    private val greetingSender: GreetingSender,
    private val currentDateProvider: CurrentDateProvider
) {
    fun execute() {
        val currentDate = currentDateProvider.get()

        friendsLoader.getAll()
            .filter { it.hasBirthdayOn(currentDate) }
            .forEach { greetingSender.sendGreetingsTo(it) }
    }

    private fun Friend.hasBirthdayOn(date: LocalDate): Boolean =
        this.dateOfBirth.month == date.month && this.dateOfBirth.dayOfMonth == date.dayOfMonth
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
