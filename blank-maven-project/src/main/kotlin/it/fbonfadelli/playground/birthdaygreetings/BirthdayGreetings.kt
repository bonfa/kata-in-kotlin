package it.fbonfadelli.playground.birthdaygreetings

import java.time.LocalDate

class BirthdayGreetings(
    private val friendsRepository: FriendsRepository,
    private val greetingSender: GreetingSender,
    private val currentDateProvider: CurrentDateProvider
) {
    fun execute() {
        val currentDate = currentDateProvider.get()

        friendsRepository.retrieveAllFriends()
            .filter { it.hasBirthdayOn(currentDate) }
            .forEach { greetingSender.sendGreetingsTo(it) }

        /*
            WHAT ABOUT
            {
                val currentDate = currentDateProvider.get()

                val friends = friendsRepository.retrieveAllFriendsHavingBirthdayOn(currentDate)

                if (friends.isNotEmpty() {
                    greetingSender.sendGreetingsTo(friends)
                }
            }
            ?
        */

    }

    private fun Friend.hasBirthdayOn(date: LocalDate): Boolean =
        this.dateOfBirth.month == date.month && this.dateOfBirth.dayOfMonth == date.dayOfMonth
}

