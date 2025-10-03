package it.fbonfadelli.playground.manhattandistance

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class ManhattanDistanceTest {

    /*

    CONSTRAINTS
    The class Point is immutable
    The class Point has no Getters, no Setters
    The class Point has no public properties (i.e. the internal state cannot be read from outside the class).

    SUGGESTED TESTS
    manhattanDistance( Point(1, 1), Point(1, 1) ) should returns 0
    manhattanDistance( Point(5, 4), Point(3, 2) ) should returns 4
    manhattanDistance( Point(1, 1), Point(0, 3) ) should returns 3

    TEST LIST
    - same point
    - changing only one coordinate (y)
    - changing only other coordinate (x)
    - changing both coordinates (x, y)
     */

    @Test
    fun `distance between a point and itself is zero`() {
        assertThat(manhattanDistance(Point(1, 1), Point(1, 1))).isEqualTo(0)
    }

    private fun manhattanDistance(
        point: Point,
        point2: Point
    ): Int {
        return 0
    }

    data class Point(private val x: Int, private val y: Int)
}