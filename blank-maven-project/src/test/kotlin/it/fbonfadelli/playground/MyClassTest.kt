package it.fbonfadelli.playground

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MyClassTest {

    /*
    - 3
    - 3 4, 5 3 - no spare no strikes
    - 3 4, 4 / 7 - spare on last frame
    - 3 4, X 3 4 - strike on last frame
    - 3 /, 3 4 - spare on not last frame
    - X  , 3 5 - strike on not last frame
    - all strikes
    - all spares
    - no spares nor strikes
     */

    @Test
    fun `single roll`() {
        val bowling = Bowling(listOf(3))

        val totalScore = bowling.totalScore()

        assertThat(totalScore).isEqualTo(3)
    }

    @Test
    fun `single roll - another score`() {
        val bowling = Bowling(listOf(4))

        val totalScore = bowling.totalScore()

        assertThat(totalScore).isEqualTo(4)
    }

    @Test
    fun `two rolls - not spare nor strike`() {
        val bowling = Bowling(listOf(3, 4))

        val totalScore = bowling.totalScore()

        assertThat(totalScore).isEqualTo(7)
    }

    @Test
    fun `many rolls - not spare nor strike`() {
        val bowling = Bowling(listOf(3, 4, 5, 4, 2))

        val totalScore = bowling.totalScore()

        assertThat(totalScore).isEqualTo(18)
    }

    @Test
    fun `a spare on the last roll`() {
        val bowling = Bowling(listOf(1, 2, 3, 7))

        val totalScore = bowling.totalScore()

        assertThat(totalScore).isEqualTo(13)
    }

    @Test
    fun `a strike on the second-last roll`() {
        val bowling = Bowling(listOf(10, 4))

        val totalScore = bowling.totalScore()

        assertThat(totalScore).isEqualTo(18)
    }

    @Test
    fun `a strike on the third to last roll`() {
        val bowling = Bowling(listOf(10, 4, 3))

        val totalScore = bowling.totalScore()

        assertThat(totalScore).isEqualTo(24)
    }
}

class Bowling(private val rolls: List<Int>) {
    fun totalScore(): Int {
        var totalScore = 0
        for (i in 0 until rolls.size) {
            val roll = rolls[i]

            if (roll == 10 && i + 1 < rolls.size && i + 2 < rolls.size) {
                totalScore += roll + rolls[i + 1] + rolls[i + 2]
            } else if (roll == 10 && i + 1 < rolls.size) {
                totalScore += roll + rolls[i + 1]
            } else {
                totalScore += roll
            }
        }
        return totalScore
    }
}

class Roll(private val score: Int) {
    fun getScore(): Int = score
}
