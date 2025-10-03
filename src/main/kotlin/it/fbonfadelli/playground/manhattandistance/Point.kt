package it.fbonfadelli.playground.manhattandistance

import kotlin.math.abs

data class Point(private val x: Int, private val y: Int) {
    fun manhattanDistanceFrom(another: Point): Int =
        abs(another.y - this.y) + abs(another.x - this.x)
}
