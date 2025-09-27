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
    fun `a strike on the last roll`() {
        val bowling = Bowling(listOf(1, 2, 10))

        val totalScore = bowling.totalScore()

        assertThat(totalScore).isEqualTo(13)
    }

    @Test
    fun `a strike on the second last roll`() {
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

    @Test
    fun `a spare on the last roll`() {
        val bowling = Bowling(listOf(3, 7))

        val totalScore = bowling.totalScore()

        assertThat(totalScore).isEqualTo(10)
    }

    @Test
    fun `a spare on the second last roll`() {
        val bowling = Bowling(listOf(3, 7, 9))

        val totalScore = bowling.totalScore()

        assertThat(totalScore).isEqualTo(28)
    }

    //3,7,3,4
}

class Bowling(private val rollScores: List<Int>) {
    fun totalScore(): Int {
        var totalScore = 0
        for (i in 0 until rollScores.size) {
            val currentRollScore = rollScores[i]

            totalScore += currentRollScore

            if (isStrike(currentRollScore)) {
                totalScore += rollScoreAt(i + 1) + rollScoreAt(i + 2)
            } else {
                val prevScore = rollScoreAt(i - 1)
                if (prevScore + currentRollScore == 10) {
                    totalScore += rollScoreAt(i + 1)
                }
            }
        }
        return totalScore
    }

    private fun isStrike(roll: Int): Boolean = roll == 10

    private fun rollScoreAt(position: Int): Int =
        rollScores.getOrNull(position) ?: 0
}
