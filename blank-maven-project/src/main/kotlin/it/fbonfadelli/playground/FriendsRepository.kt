package it.fbonfadelli.playground

import java.time.LocalDate

interface FriendsRepository {
    fun retrieveAllFriends(): List<Friend>
}

class Friend(
    val firstName: String,
    val lastName: String,
    val dateOfBirth: LocalDate,
    val email: String,
)