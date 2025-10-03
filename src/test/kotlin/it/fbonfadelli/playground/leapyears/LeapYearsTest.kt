package it.fbonfadelli.playground.leapyears

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.time.Year

class LeapYearsTest {

    /*
    All years divisible by 400 ARE leap years (so, for example, 2000 was indeed a leap year),
    All years divisible by 100 but not by 400 are NOT leap years (so, for example, 1700, 1800, and 1900 were NOT leap years, NOR will 2100 be a leap year),
    All years divisible by 4 but not by 100 ARE leap years (e.g., 2008, 2012, 2016),
    All years not divisible by 4 are NOT leap years (e.g. 2017, 2018, 2019).

    - 1700 --> not leap year
    - 2000 --> leap year

     */

    @Test
    fun `odd year`() {
        assertThat(isLeapYear(2017)).isFalse
    }

    @Test
    fun `year which is divisible by 4`() {
        assertThat(isLeapYear(2016)).isTrue
    }

    @Test
    fun `even year - not divisible by four`() {
        assertThat(isLeapYear(2018)).isFalse
    }

    @Test
    fun `year divisible by four and by 100 but not by 400`() {
        assertThat(isLeapYear(1900)).isFalse
    }

    private fun isLeapYear(year: Int): Boolean {
        return if (year % 100 == 0) false else year % 4 == 0
    }
}