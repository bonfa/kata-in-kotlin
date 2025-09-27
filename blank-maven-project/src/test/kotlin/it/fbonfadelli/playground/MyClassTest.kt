package it.fbonfadelli.playground

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MyClassTest {

    @Test
    fun `many rolls - not spare nor strike`() {
        val bowling = Bowling(listOf(3, 4, 5, 4, 2, 4))

        val totalScore = bowling.totalScore()

        assertThat(totalScore).isEqualTo(22)
    }

    @Test
    fun `a strike on the last roll`() {
        val bowling = Bowling(listOf(1, 9, 10))

        val totalScore = bowling.totalScore()

        assertThat(totalScore).isEqualTo(20)
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

sealed interface Frame {
    fun getFirstRollScore(): Int
    fun getSecondRollScore(): Int?
}

object Strike : Frame {
    fun getScore(nextFrame: Frame, secondNextFrame: Frame?): Int =
        10 + nextFrame.getFirstRollScore() + (nextFrame.getSecondRollScore() ?: secondNextFrame?.getFirstRollScore()
        ?: 0)

    override fun getFirstRollScore(): Int = 10

    override fun getSecondRollScore(): Int? = null
}

class Spare(private val firstRollScore: Int) : Frame {
    fun getScore(nextFrame: Frame): Int =
        10 + nextFrame.getFirstRollScore()

    override fun getFirstRollScore(): Int =
        firstRollScore

    override fun getSecondRollScore(): Int = 10 - firstRollScore
}

class NorStrikeNorSpare(
    private val firstRollScore: Int,
    private val secondRollScore: Int
) : Frame {
    fun getScore(): Int =
        firstRollScore + secondRollScore

    override fun getFirstRollScore(): Int =
        firstRollScore

    override fun getSecondRollScore(): Int =
        secondRollScore
}

class LastFrame(
    private val firstRollScore: Int,
    private val secondRollScore: Int,
    private val thirdRollScore: Int = 0,
) : Frame {
    fun getScore(): Int =
        firstRollScore + secondRollScore + thirdRollScore

    override fun getFirstRollScore(): Int =
        firstRollScore

    override fun getSecondRollScore(): Int =
        secondRollScore
}

class BowlingGame(private val frames: List<Frame>) {
    fun totalScore(): Int {
        var totalScore = 0
        for (i in 0 until frames.size) {
            totalScore += when (val frame = frames[i]) {
                is LastFrame -> frame.getScore()
                is NorStrikeNorSpare -> frame.getScore()
                is Spare -> frame.getScore(frames[i + 1])
                is Strike -> frame.getScore(frames[i + 1], frames.getOrNull(i + 2))
            }
        }
        return totalScore
    }
}

class Bowling(private val rollScores: List<Int>) {

    fun totalScore(): Int {
        return BowlingGame(createFrames(rollScores)).totalScore()
    }

    private fun createFrames(rollScores: List<Int>): List<Frame> {
        val frames = mutableListOf<Frame>()

        var i = 0
        var prevScore: Int? = null
        while (prevScore != null || i < rollScores.size - 3)  {
            val rollScore = rollScores[i]
            if (rollScore == 10) {
                frames.add(Strike)
                prevScore = null
                i++
            } else if (prevScore == null) {
                prevScore = rollScore
                i++
            } else {
                if (prevScore + rollScore == 10) {
                    frames.add(Spare(prevScore))
                } else {
                    frames.add(NorStrikeNorSpare(prevScore, rollScore))
                }
                prevScore = null
                i++
            }
        }

        frames.add(LastFrame(rollScores[i], rollScores[i + 1], rollScores.getOrNull(i + 2) ?: 0))

        return frames
    }
}
