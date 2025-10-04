package it.fbonfadelli.playground.marsrover

import it.fbonfadelli.playground.marsrover.Command.MOVE_FORWARD
import it.fbonfadelli.playground.marsrover.Command.ROTATE_LEFT
import it.fbonfadelli.playground.marsrover.Command.ROTATE_RIGHT
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
     */

    @Test
    fun `1d space, move forward`() {
        val rover = Rover(0, listOf(MOVE_FORWARD))

        val finalPosition = rover.finalPosition()

        assertThat(finalPosition).isEqualTo(1)
    }

    @Test
    fun `1d space, move forward twice`() {
        val rover = Rover(0, listOf(MOVE_FORWARD, MOVE_FORWARD))

        val finalPosition = rover.finalPosition()

        assertThat(finalPosition).isEqualTo(2)
    }

    @Test
    fun `1d space, different initial position, move forward twice`() {
        val rover = Rover(2, listOf(MOVE_FORWARD, MOVE_FORWARD))

        val finalPosition = rover.finalPosition()

        assertThat(finalPosition).isEqualTo(4)
    }

    @Test
    fun `1d space, different initial position, rotate left`() {
        val rover = Rover(2, listOf(ROTATE_LEFT))

        val finalPosition = rover.finalPosition()

        assertThat(finalPosition).isEqualTo(2)
    }

    @Test
    fun `1d space, different initial position, rotate right`() {
        val rover = Rover(2, listOf(ROTATE_RIGHT))

        val finalPosition = rover.finalPosition()

        assertThat(finalPosition).isEqualTo(2)
    }

    @Test
    fun `1d space, move forward, final facing`() {
        val rover = Rover(2, listOf(MOVE_FORWARD))

        val finalPosition = rover.finalFacing()

        assertThat(finalPosition).isEqualTo(Direction.NORTH)
    }
}

class Rover(
    private val initialPosition: Int,
    private val newCommands: List<Command>,
) {
    fun finalPosition(): Int {
        return initialPosition + positionIncrement()
    }

    private fun positionIncrement(): Int =
        newCommands.map { if (it == MOVE_FORWARD) 1 else 0 }.sum()

    fun finalFacing(): Direction =
        Direction.NORTH

}

enum class Command {
    MOVE_FORWARD,
    ROTATE_LEFT,
    ROTATE_RIGHT,
}

enum class Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST,
}