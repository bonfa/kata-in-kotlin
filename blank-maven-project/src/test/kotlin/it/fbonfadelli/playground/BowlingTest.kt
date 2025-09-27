package it.fbonfadelli.playground

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BowlingTest {

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
