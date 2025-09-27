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
    fun `a strike on a frame which is not the last one`() {
        val bowling = Bowling(listOf(10, 4, 5, 0, 0))

        val totalScore = bowling.totalScore()

        assertThat(totalScore).isEqualTo(28)
    }

    @Test
    fun `a spare on a frame which is not the last one`() {
        val bowling = Bowling(listOf(3, 7, 9, 0))

        val totalScore = bowling.totalScore()

        assertThat(totalScore).isEqualTo(28)
    }

    @Test
    fun `a spare is valid only within a frame and not for every coule of values which have sum = 10`() {
        val bowling = Bowling(listOf(3, 7, 3, 4))

        val totalScore = bowling.totalScore()

        assertThat(totalScore).isEqualTo(20)
    }

    @Test
    fun `last frame - strike on the first roll`() {
        val bowling = Bowling(listOf(10, 3, 4))

        val totalScore = bowling.totalScore()

        assertThat(totalScore).isEqualTo(17)
    }

    @Test
    fun `last frame - strike on the first and the second roll`() {
        val bowling = Bowling(listOf(10, 10, 4))

        val totalScore = bowling.totalScore()

        assertThat(totalScore).isEqualTo(24)
    }

    @Test
    fun `last frame - strike on the first and the second roll - other values`() {
        val bowling = Bowling(listOf(10, 0, 4))

        val totalScore = bowling.totalScore()

        assertThat(totalScore).isEqualTo(14)
    }

    @Test
    fun `last frame - spare on the second roll`() {
        val bowling = Bowling(listOf(4, 6, 3))

        val totalScore = bowling.totalScore()

        assertThat(totalScore).isEqualTo(13)
    }

    @Test
    fun `acceptance 1 - all strikes`() {
        val bowling = Bowling(listOf(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10))

        val totalScore = bowling.totalScore()

        assertThat(totalScore).isEqualTo(300)
    }

    @Test
    fun `acceptance 2 - no spares nor strikes`() {
        val bowling = Bowling(listOf(9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0))

        val totalScore = bowling.totalScore()

        assertThat(totalScore).isEqualTo(90)
    }

    @Test
    fun `acceptance 3 - all spares and five as last roll`() {
        val bowling = Bowling(listOf(5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5))

        val totalScore = bowling.totalScore()

        assertThat(totalScore).isEqualTo(150)
    }


}

class Bowling(private val rollScores: List<Int>) {

    private var isNewFrame = true

    fun totalScore(): Int {
        var totalScore = 0
        for (i in 0 until rollScores.size) {
            totalScore += currentFrameScore(i)
            if (isStrike(rollScoreAt(i)) && isLastFrame(i)) break
            if (isSpare(i,rollScoreAt(i)) && isLastFrame(i - 1)) break
        }
        return totalScore
    }

    private fun currentFrameScore(rollPosition: Int): Int =
        when {
            isStrike(rollScoreAt(rollPosition)) -> {
                isNewFrame = !isLastFrame(rollPosition)
                rollScoreAt(rollPosition) + rollScoreAt(rollPosition + 1) + rollScoreAt(rollPosition + 2)
            }

            isSpare(rollPosition, rollScoreAt(rollPosition)) -> {
                isNewFrame = if (isLastFrame(rollPosition)) false else !isNewFrame
                rollScoreAt(rollPosition) + rollScoreAt(rollPosition + 1)
            }

            else -> {
                isNewFrame = !isNewFrame
                rollScoreAt(rollPosition)
            }
        }

    private fun isLastFrame(rollPosition: Int): Boolean {
        return rollPosition in (rollScores.size - 3) until rollScores.size
    }

    private fun isSpare(i: Int, currentRollScore: Int): Boolean =
        !isNewFrame && rollScoreAt(i - 1) + currentRollScore == 10

    private fun isStrike(roll: Int): Boolean = roll == 10

    private fun rollScoreAt(position: Int): Int =
        rollScores.getOrNull(position) ?: 0
}
