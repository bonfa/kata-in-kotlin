package it.fbonfadelli.playground.leapyears

fun isLeapYear(year: Int): Boolean = when {
    year % 400 == 0 -> true
    year % 100 == 0 -> false
    year % 4 == 0 -> true
    else -> false
}