package it.fbonfadelli.playground.manhattandistance

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ManhattanDistanceTest {

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

    @Test
    fun `points with same y - x of first point is smaller than the y of second point`() {
        assertThat(manhattanDistance(Point(1, 4), Point(9, 4))).isEqualTo(8)
    }

    @Test
    fun `points with same y - x of first point is greater than the y of second point`() {
        assertThat(manhattanDistance(Point(10, 4), Point(0, 4))).isEqualTo(10)
    }

    @Test
    fun `different x and y`() {
        assertThat(manhattanDistance(Point(5, 4), Point(3, 2))).isEqualTo(4)
    }

    @Test
    fun `different x and y - another case`() {
        assertThat(manhattanDistance(Point(1, 1), Point(0, 3))).isEqualTo(3)
    }
}
