package it.fbonfadelli.playground.marsrover

import it.fbonfadelli.playground.marsrover.Command.MoveForward
import it.fbonfadelli.playground.marsrover.Command.RotateLeft
import it.fbonfadelli.playground.marsrover.Command.RotateRight
import it.fbonfadelli.playground.marsrover.Direction.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
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
        val rover = Rover(listOf(MoveForward), roverState = RoverState(0, NORTH))

        val finalPosition = rover.finalPosition()

        assertThat(finalPosition).isEqualTo(1)
    }

    @Test
    fun `1d space, default starting position, standard direction, move forward twice`() {
        val rover = Rover(listOf(MoveForward, MoveForward), roverState = RoverState(0, NORTH))

        val finalPosition = rover.finalPosition()

        assertThat(finalPosition).isEqualTo(2)
    }

    @Test
    fun `1d space, different initial position, standard direction, move forward twice`() {
        val rover = Rover(listOf(MoveForward, MoveForward), roverState = RoverState(2, NORTH))

        val finalPosition = rover.finalPosition()

        assertThat(finalPosition).isEqualTo(4)
    }

    @Test
    fun `1d space, different initial position, standard direction, rotate left`() {
        val rover = Rover(listOf(RotateLeft), roverState = RoverState(2, NORTH))

        val finalPosition = rover.finalPosition()

        assertThat(finalPosition).isEqualTo(2)
    }

    @Test
    fun `1d space, different initial position, standard direction, rotate right`() {
        val rover = Rover(listOf(RotateRight), roverState = RoverState(2, NORTH))

        val finalPosition = rover.finalPosition()

        assertThat(finalPosition).isEqualTo(2)
    }

    @Test
    fun `1d space, different initial position, standard direction, move forward`() {
        val rover = Rover(listOf(MoveForward), roverState = RoverState(2, NORTH))

        val finalFacing = rover.finalFacing()

        assertThat(finalFacing).isEqualTo(NORTH)
    }

    @Test
    fun `1d space, different initial position, different initial direction, move forward`() {
        val rover = Rover(listOf(MoveForward), roverState = RoverState(2, SOUTH))

        val finalFacing = rover.finalFacing()

        assertThat(finalFacing).isEqualTo(SOUTH)
    }

    @Test
    fun `1d space, different initial position, standard initial direction, rotate left`() {
        val rover = Rover(listOf(RotateLeft), roverState = RoverState(2, NORTH))

        val finalFacing = rover.finalFacing()

        assertThat(finalFacing).isEqualTo(WEST)
    }

    @Test
    fun `1d space, different initial position, initial direction is WEST, rotate left`() {
        val rover = Rover(listOf(RotateLeft), roverState = RoverState(2, WEST))

        val finalFacing = rover.finalFacing()

        assertThat(finalFacing).isEqualTo(SOUTH)
    }

    @Test
    fun `1d space, different initial position, initial direction is SOUTH, rotate left`() {
        val rover = Rover(listOf(RotateLeft), roverState = RoverState(2, SOUTH))

        val finalFacing = rover.finalFacing()

        assertThat(finalFacing).isEqualTo(EAST)
    }

    @Test
    fun `1d space, different initial position, initial direction is EAST, rotate left`() {
        val rover = Rover(listOf(RotateLeft), roverState = RoverState(2, EAST))

        val finalFacing = rover.finalFacing()

        assertThat(finalFacing).isEqualTo(NORTH)
    }

    @Test
    fun `1d space, different initial position, standard initial direction, rotate right`() {
        val rover = Rover(listOf(RotateRight), roverState = RoverState(2, NORTH))

        val finalFacing = rover.finalFacing()

        assertThat(finalFacing).isEqualTo(EAST)
    }

    @Test
    fun `1d space, different initial position, initial direction is WEST, rotate right`() {
        val rover = Rover(listOf(RotateRight), roverState = RoverState(2, EAST))

        val finalFacing = rover.finalFacing()

        assertThat(finalFacing).isEqualTo(SOUTH)
    }

    @Test
    fun `1d space, different initial position, initial direction is SOUTH, rotate right`() {
        val rover = Rover(listOf(RotateRight), roverState = RoverState(2, SOUTH))

        val finalFacing = rover.finalFacing()

        assertThat(finalFacing).isEqualTo(WEST)
    }

    @Test
    fun `1d space, different initial position, initial direction is EAST, rotate right`() {
        val rover = Rover(listOf(RotateRight), roverState = RoverState(2, WEST))

        val finalFacing = rover.finalFacing()

        assertThat(finalFacing).isEqualTo(NORTH)
    }

    @Test
    fun `1d space, initial direction is EAST, rotate right twice`() {
        val rover = Rover(listOf(RotateRight, RotateRight), roverState = RoverState(2, WEST))

        val finalFacing = rover.finalFacing()

        assertThat(finalFacing).isEqualTo(EAST)
    }

    @Test
    fun `1d space, initialDirection is SOUTH, move forward`() {
        val rover = Rover(listOf(MoveForward), roverState = RoverState(2, SOUTH))

        val finalPosition = rover.finalPosition()

        assertThat(finalPosition).isEqualTo(1)
    }

    @Test
    @Disabled
    fun `1d space, initialDirection is SOUTH, a mix of rotations and moveForward`() {
        val rover = Rover(
            listOf(MoveForward, RotateRight, RotateRight, MoveForward),
            roverState = RoverState(2, SOUTH)
        )

        val finalPosition = rover.finalPosition()

        assertThat(finalPosition).isEqualTo(2)
    }
}

data class RoverState(
    val position: Int, //Y
    val direction: Direction,
)

class Rover(
    private val newCommands: List<Command>,
    private val roverState: RoverState
) {
    fun finalPosition(): Int =
        newCommands.fold(roverState) { currentState, command ->
            command.nextPosition(currentState)
        }.position

    fun finalFacing(): Direction =
        newCommands.fold(roverState.direction) { currentDirection, command ->
            command.nextDirection(currentDirection)
        }
}

sealed interface Command {
    fun nextDirection(currentDirection: Direction): Direction
    fun nextPosition(currentState: RoverState): RoverState

    data object MoveForward : Command {
        override fun nextDirection(currentDirection: Direction): Direction =
            currentDirection

        override fun nextPosition(currentState: RoverState): RoverState =
            if (currentState.direction == SOUTH)
                currentState.copy(position = currentState.position - 1)
            else
                currentState.copy(position = currentState.position + 1)
    }

    data object RotateLeft : Command {
        override fun nextDirection(currentDirection: Direction): Direction =
            when (currentDirection) {
                WEST -> SOUTH
                SOUTH -> EAST
                EAST -> NORTH
                NORTH -> WEST
            }

        override fun nextPosition(currentState: RoverState): RoverState =
            currentState
    }

    data object RotateRight : Command {
        override fun nextDirection(currentDirection: Direction): Direction =
            when (currentDirection) {
                NORTH -> EAST
                EAST -> SOUTH
                SOUTH -> WEST
                WEST -> NORTH
            }

        override fun nextPosition(currentState: RoverState): RoverState =
            currentState
    }
}

enum class Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST,
}