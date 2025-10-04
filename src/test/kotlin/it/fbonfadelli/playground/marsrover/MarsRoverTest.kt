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
    fun `1d space, default starting position, standard direction, move forward`() {
        val rover = Rover(0, Direction.NORTH, listOf(MOVE_FORWARD))

        val finalPosition = rover.finalPosition()

        assertThat(finalPosition).isEqualTo(1)
    }

    @Test
    fun `1d space, default starting position, standard direction, move forward twice`() {
        val rover = Rover(0, Direction.NORTH, listOf(MOVE_FORWARD, MOVE_FORWARD))

        val finalPosition = rover.finalPosition()

        assertThat(finalPosition).isEqualTo(2)
    }

    @Test
    fun `1d space, different initial position, standard direction, move forward twice`() {
        val rover = Rover(2, Direction.NORTH, listOf(MOVE_FORWARD, MOVE_FORWARD))

        val finalPosition = rover.finalPosition()

        assertThat(finalPosition).isEqualTo(4)
    }

    @Test
    fun `1d space, different initial position, standard direction, rotate left`() {
        val rover = Rover(2, Direction.NORTH, listOf(ROTATE_LEFT))

        val finalPosition = rover.finalPosition()

        assertThat(finalPosition).isEqualTo(2)
    }

    @Test
    fun `1d space, different initial position, standard direction, rotate right`() {
        val rover = Rover(2, Direction.NORTH, listOf(ROTATE_RIGHT))

        val finalPosition = rover.finalPosition()

        assertThat(finalPosition).isEqualTo(2)
    }

    @Test
    fun `1d space, different initial position, standard direction, move forward`() {
        val rover = Rover(2, Direction.NORTH, listOf(MOVE_FORWARD))

        val finalPosition = rover.finalFacing()

        assertThat(finalPosition).isEqualTo(Direction.NORTH)
    }

    @Test
    fun `1d space, different initial position, different initial direction, move forward`() {
        val rover = Rover(2, Direction.SOUTH, listOf(MOVE_FORWARD))

        val finalPosition = rover.finalFacing()

        assertThat(finalPosition).isEqualTo(Direction.SOUTH)
    }

    @Test
    fun `1d space, different initial position, standard initial direction, rotate left`() {
        val rover = Rover(2, Direction.NORTH, listOf(ROTATE_LEFT))

        val finalPosition = rover.finalFacing()

        assertThat(finalPosition).isEqualTo(Direction.WEST)
    }

    @Test
    fun `1d space, different initial position, initial direction is WEST, rotate left`() {
        val rover = Rover(2, Direction.WEST, listOf(ROTATE_LEFT))

        val finalPosition = rover.finalFacing()

        assertThat(finalPosition).isEqualTo(Direction.SOUTH)
    }

    @Test
    fun `1d space, different initial position, initial direction is SOUTH, rotate left`() {
        val rover = Rover(2, Direction.SOUTH, listOf(ROTATE_LEFT))

        val finalPosition = rover.finalFacing()

        assertThat(finalPosition).isEqualTo(Direction.EAST)
    }

    @Test
    fun `1d space, different initial position, initial direction is EAST, rotate left`() {
        val rover = Rover(2, Direction.EAST, listOf(ROTATE_LEFT))

        val finalPosition = rover.finalFacing()

        assertThat(finalPosition).isEqualTo(Direction.NORTH)
    }
}

class Rover(
    private val initialPosition: Int,
    private val initialDirection: Direction,
    private val newCommands: List<Command>,
) {
    fun finalPosition(): Int {
        return initialPosition + positionIncrement()
    }

    fun finalFacing(): Direction {
        val command = newCommands.first()
        if (command == ROTATE_LEFT) {
            if (initialDirection == Direction.WEST)
                return Direction.SOUTH
            if (initialDirection == Direction.SOUTH)
                return Direction.EAST
            if (initialDirection == Direction.EAST)
                return Direction.NORTH
            return Direction.WEST

        }
        return initialDirection
    }

    private fun positionIncrement(): Int =
        newCommands.map { if (it == MOVE_FORWARD) 1 else 0 }.sum()

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