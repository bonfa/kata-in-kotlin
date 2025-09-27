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

//    @Test
//    fun `two rolls - not spare nor strike`() {
//        val bowling = Bowling(listOf(3))
//
//        val totalScore = bowling.totalScore()
//
//        assertThat(totalScore).isEqualTo(3)
//    }
}

class Bowling(private val rolls: List<Int>) {
    fun totalScore(): Int {
        return rolls.first()
    }
}

class Roll(private val score: Int) {
    fun getScore(): Int = score
}
