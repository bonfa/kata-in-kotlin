package it.fbonfadelli.playground.manhattandistance

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import kotlin.math.abs

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
    - changing only one coordinate (y) [x]
    - changing only other coordinate (x)
    - changing both coordinates (x, y)
     */

    @Test
    fun `distance between a point and itself is zero`() {
        assertThat(manhattanDistance(Point(1, 1), Point(1, 1))).isEqualTo(0)
    }

    @Test
    fun `points with same x - y of first point is smaller than the y of second point`() {
        assertThat(manhattanDistance(Point(1, 1), Point(1, 2))).isEqualTo(1)
    }

    @Test
    fun `points with same x - y of first point is greater than the y of second point`() {
        assertThat(manhattanDistance(Point(1, 4), Point(1, 2))).isEqualTo(2)
    }

    private fun manhattanDistance(
        point: Point,
        point2: Point
    ): Int {
        return point.manhattanDistanceFrom(point2)
    }

    data class Point(private val x: Int, private val y: Int) {
        fun manhattanDistanceFrom(point2: Point): Int {
            return abs(point2.y - this.y)
        }
    }
}