package it.fbonfadelli.playground

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MyClassTest {

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

    @Test
    fun `a spare is valid only within a frame and not for every coule of values which have sum = 10`() {
        val bowling = Bowling(listOf(3, 7, 3, 4))

        val totalScore = bowling.totalScore()

        assertThat(totalScore).isEqualTo(20)
    }
}

class Bowling(private val rollScores: List<Int>) {

    private var isNewFrame = true

    fun totalScore(): Int {
        var totalScore = 0
        for (i in 0 until rollScores.size) {
            totalScore += currentFrameScore(i)
        }
        return totalScore
    }

    private fun currentFrameScore(rollPosition: Int): Int =
        when {
            isStrike(rollScoreAt(rollPosition)) -> {
                isNewFrame = true
                rollScoreAt(rollPosition) + rollScoreAt(rollPosition + 1) + rollScoreAt(rollPosition + 2)
            }

            isSpare(rollPosition, rollScoreAt(rollPosition)) -> {
                isNewFrame = !isNewFrame
                rollScoreAt(rollPosition) + rollScoreAt(rollPosition + 1)
            }

            else -> {
                isNewFrame = !isNewFrame
                rollScoreAt(rollPosition)
            }
        }

    private fun isSpare(i: Int, currentRollScore: Int): Boolean =
        !isNewFrame && rollScoreAt(i - 1) + currentRollScore == 10

    private fun isStrike(roll: Int): Boolean = roll == 10

    private fun rollScoreAt(position: Int): Int =
        rollScores.getOrNull(position) ?: 0
}
