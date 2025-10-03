package it.fbonfadelli.playground.leapyears

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.time.Year

class LeapYearsTest {

    @Test
    fun `odd year`() {
        assertThat(isLeapYear(2017)).isFalse
    }

    @Test
    fun `year divisible by 4`() {
        assertThat(isLeapYear(2016)).isTrue
    }

    @Test
    fun `even year not divisible by four`() {
        assertThat(isLeapYear(2018)).isFalse
    }

    @Test
    fun `year divisible by 100 but not by 400`() {
        assertThat(isLeapYear(1900)).isFalse
    }

    @Test
    fun `year divisible by 400`() {
        assertThat(isLeapYear(2000)).isTrue
    }

    private fun isLeapYear(year: Int): Boolean = when {
        year % 400 == 0 -> true
        year % 100 == 0 -> false
        year % 4 == 0 -> true
        else -> false
    }
}