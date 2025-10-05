package it.fbonfadelli.playground.marsrover

import it.fbonfadelli.playground.marsrover.Command.MoveForward
import it.fbonfadelli.playground.marsrover.Command.RotateLeft
import it.fbonfadelli.playground.marsrover.Command.RotateRight
import it.fbonfadelli.playground.marsrover.Direction.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MarsRoverTest {

    /*
    DIMENSIONS
     - space direction (1D, 2D)
     - facing / no facing
     - commands (forward, turn right, turn left)
        - 1 command at the time
     - 1 / more commands
     - obstacles / no obstacles

     TESTS
     - map: 1D, 1 command (forward)
     - map: 1D, 2 commands (forward, forward)
     - map: 1D, 1 command (turn right)
     - map: 1D, 1 command (turn left)
     - map: 1D, 2 commands (forward, turn right)
     - map: 1D, 2 commands (forward, turn left)
     - map: 2D, 1 command (turn right)



     SPACE
             NORTH
             Y |
               |
               |
               |
               |--------------->
WEST                       X EAST

               SOUTH
     */

    @Test
    fun `1d space, default starting position, standard direction, move forward`() {
        val rover = Rover(0, NORTH, listOf(MoveForward))

        val finalPosition = rover.finalPosition()

        assertThat(finalPosition).isEqualTo(1)
    }

    @Test
    fun `1d space, default starting position, standard direction, move forward twice`() {
        val rover = Rover(0, NORTH, listOf(MoveForward, MoveForward))

        val finalPosition = rover.finalPosition()

        assertThat(finalPosition).isEqualTo(2)
    }

    @Test
    fun `1d space, different initial position, standard direction, move forward twice`() {
        val rover = Rover(2, NORTH, listOf(MoveForward, MoveForward))

        val finalPosition = rover.finalPosition()

        assertThat(finalPosition).isEqualTo(4)
    }

    @Test
    fun `1d space, different initial position, standard direction, rotate left`() {
        val rover = Rover(2, NORTH, listOf(RotateLeft))

        val finalPosition = rover.finalPosition()

        assertThat(finalPosition).isEqualTo(2)
    }

    @Test
    fun `1d space, different initial position, standard direction, rotate right`() {
        val rover = Rover(2, NORTH, listOf(RotateRight))

        val finalPosition = rover.finalPosition()

        assertThat(finalPosition).isEqualTo(2)
    }

    @Test
    fun `1d space, different initial position, standard direction, move forward`() {
        val rover = Rover(2, NORTH, listOf(MoveForward))

        val finalFacing = rover.finalFacing()

        assertThat(finalFacing).isEqualTo(NORTH)
    }

    @Test
    fun `1d space, different initial position, different initial direction, move forward`() {
        val rover = Rover(2, SOUTH, listOf(MoveForward))

        val finalFacing = rover.finalFacing()

        assertThat(finalFacing).isEqualTo(SOUTH)
    }

    @Test
    fun `1d space, different initial position, standard initial direction, rotate left`() {
        val rover = Rover(2, NORTH, listOf(RotateLeft))

        val finalFacing = rover.finalFacing()

        assertThat(finalFacing).isEqualTo(WEST)
    }

    @Test
    fun `1d space, different initial position, initial direction is WEST, rotate left`() {
        val rover = Rover(2, WEST, listOf(RotateLeft))

        val finalFacing = rover.finalFacing()

        assertThat(finalFacing).isEqualTo(SOUTH)
    }

    @Test
    fun `1d space, different initial position, initial direction is SOUTH, rotate left`() {
        val rover = Rover(2, SOUTH, listOf(RotateLeft))

        val finalFacing = rover.finalFacing()

        assertThat(finalFacing).isEqualTo(EAST)
    }

    @Test
    fun `1d space, different initial position, initial direction is EAST, rotate left`() {
        val rover = Rover(2, EAST, listOf(RotateLeft))

        val finalFacing = rover.finalFacing()

        assertThat(finalFacing).isEqualTo(NORTH)
    }

    @Test
    fun `1d space, different initial position, standard initial direction, rotate right`() {
        val rover = Rover(2, NORTH, listOf(RotateRight))

        val finalFacing = rover.finalFacing()

        assertThat(finalFacing).isEqualTo(EAST)
    }

    @Test
    fun `1d space, different initial position, initial direction is WEST, rotate right`() {
        val rover = Rover(2, EAST, listOf(RotateRight))

        val finalFacing = rover.finalFacing()

        assertThat(finalFacing).isEqualTo(SOUTH)
    }

    @Test
    fun `1d space, different initial position, initial direction is SOUTH, rotate right`() {
        val rover = Rover(2, SOUTH, listOf(RotateRight))

        val finalFacing = rover.finalFacing()

        assertThat(finalFacing).isEqualTo(WEST)
    }

    @Test
    fun `1d space, different initial position, initial direction is EAST, rotate right`() {
        val rover = Rover(2, Direction.WEST, listOf(RotateRight))

        val finalFacing = rover.finalFacing()

        assertThat(finalFacing).isEqualTo(NORTH)
    }

    @Test
    fun `1d space, initial direction is EAST, rotate right twice`() {
        val rover = Rover(2, WEST, listOf(RotateRight, RotateRight))

        val finalFacing = rover.finalFacing()

        assertThat(finalFacing).isEqualTo(EAST)
    }
}

class Rover(
    private val initialPosition: Int, //Y
    private val initialDirection: Direction,
    private val newCommands: List<Command>,
) {
    fun finalPosition(): Int =
        newCommands.fold(initialPosition) { currentPosition, command ->
            command.nextPosition(currentPosition)
        }

    fun finalFacing(): Direction =
        newCommands.fold(initialDirection) { currentDirection, command ->
            command.nextDirection(currentDirection)
        }
}

sealed interface Command {
    fun nextDirection(currentDirection: Direction): Direction
    fun nextPosition(currentPosition: Int): Int

    data object MoveForward : Command {
        override fun nextDirection(currentDirection: Direction): Direction =
            currentDirection

        override fun nextPosition(currentPosition: Int): Int =
            currentPosition + 1
    }

    data object RotateLeft : Command {
        override fun nextDirection(currentDirection: Direction): Direction =
            when (currentDirection) {
                WEST -> SOUTH
                SOUTH -> EAST
                EAST -> NORTH
                NORTH -> WEST
            }

        override fun nextPosition(currentPosition: Int): Int =
            currentPosition
    }

    data object RotateRight : Command {
        override fun nextDirection(currentDirection: Direction): Direction =
            when (currentDirection) {
                NORTH -> EAST
                EAST -> SOUTH
                SOUTH -> WEST
                WEST -> NORTH
            }

        override fun nextPosition(currentPosition: Int): Int =
            currentPosition
    }
}

enum class Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST,
}