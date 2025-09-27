package it.fbonfadelli.playground.birthdaygreetings

import java.time.LocalDate

interface CurrentDateProvider {
    fun get(): LocalDate
}