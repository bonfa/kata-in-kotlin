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

    // MOVE FORWARD
    @Test
    fun `1d space - move forward once`() {
        val rover = Rover(state = RoverState(NORTH, Position(0, 0)), commands = listOf(MoveForward))

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(NORTH, Position(0, 1)))
    }

    @Test
    fun `1d space - move forward twice`() {
        val rover = Rover(state = RoverState(NORTH, Position(0, 0)), commands = listOf(MoveForward, MoveForward))

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(NORTH, Position(0, 2)))
    }

    @Test
    fun `1d space - different initial position - move forward twice`() {
        val rover = Rover(state = RoverState(NORTH, Position(0, 2)), commands = listOf(MoveForward, MoveForward))

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(NORTH, Position(0, 4)))
    }

    @Test
    fun `1d space - initial direction is SOUTH - move forward`() {
        val rover = Rover(state = RoverState(SOUTH, Position(0, 2)), commands = listOf(MoveForward))

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(SOUTH, Position(0, 1)))
    }

    // ROTATE LEFT
    @Test
    fun `1d space - initial direction is NORTH - rotate left`() {
        val rover = Rover(state = RoverState(NORTH, Position(0, 2)), commands = listOf(RotateLeft))

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(WEST, Position(0, 2)))
    }

    @Test
    fun `1d space- - initial direction is WEST - rotate left`() {
        val rover = Rover(state = RoverState(WEST, Position(0, 2)), commands = listOf(RotateLeft))

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(SOUTH, Position(0, 2)))
    }

    @Test
    fun `1d space - initial direction is SOUTH - rotate left`() {
        val rover = Rover(state = RoverState(SOUTH, Position(0, 2)), commands = listOf(RotateLeft))

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(EAST, Position(0, 2)))
    }

    @Test
    fun `1d space - initial direction is EAST - rotate left`() {
        val rover = Rover(state = RoverState(EAST, Position(0, 2)), commands = listOf(RotateLeft))

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(NORTH, Position(0, 2)))
    }

    // ROTATE RIGHT
    @Test
    fun `1d space - initial direction is NORTH - rotate right`() {
        val rover = Rover(state = RoverState(NORTH, Position(0, 2)), commands = listOf(RotateRight))

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(EAST, Position(0, 2)))
    }

    @Test
    fun `1d space - initial direction is WEST - rotate right`() {
        val rover = Rover(state = RoverState(EAST, Position(0, 2)), commands = listOf(RotateRight))

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(SOUTH, Position(0, 2)))
    }

    @Test
    fun `1d space - initial direction is SOUTH - rotate right`() {
        val rover = Rover(state = RoverState(SOUTH, Position(0, 2)), commands = listOf(RotateRight))

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(WEST, Position(0, 2)))
    }

    @Test
    fun `1d space - initial direction is EAST, rotate right`() {
        val rover = Rover(state = RoverState(WEST, Position(0, 2)), commands = listOf(RotateRight))

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(NORTH, Position(0, 2)))
    }

    @Test
    fun `1d space - initial direction is EAST - rotate right twice`() {
        val rover = Rover(state = RoverState(WEST, Position(0, 2)), commands = listOf(RotateRight, RotateRight))

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(EAST, Position(0, 2)))
    }

    @Test
    fun `1d space, initialDirection is SOUTH, a mix of rotations and moveForward`() {
        val rover = Rover(
            state = RoverState(SOUTH, Position(0, 2)),
            listOf(MoveForward, RotateRight, RotateRight, MoveForward)
        )

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(NORTH, Position(0, 2)))
    }

    @Test
    fun `2d space - move forward towards EAST`() {
        val rover = Rover(
            state = RoverState(EAST, Position(0, 2)),
            listOf(MoveForward)
        )

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(EAST, Position(1, 2)))
    }

    @Test
    fun `2d space - move forward towards WEST`() {
        val rover = Rover(
            state = RoverState(WEST, Position(0, 2)),
            commands = listOf(MoveForward)
        )

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(WEST, Position(-1, 2)))
    }
}

data class RoverState(
    val direction: Direction,
    val position: Position,
)

data class Position(
    val x: Int,
    val y: Int
)

class Rover(
    private val state: RoverState,
    private val commands: List<Command>
) {
    fun finalState(): RoverState =
        commands.fold(state) { currentState, command ->
            command.nextState(currentState)
        }
}

sealed interface Command {
    fun nextDirection(currentDirection: Direction): Direction
    fun nextPosition(currentState: RoverState): Position

    fun nextState(currentState: RoverState): RoverState =
        RoverState(
            direction = nextDirection(currentState.direction),
            position = nextPosition(currentState),
        )

    data object MoveForward : Command {
        override fun nextDirection(currentDirection: Direction): Direction =
            currentDirection

        override fun nextPosition(currentState: RoverState): Position =
            when (currentState.direction) {
                EAST -> currentState.position.copy(x = currentState.position.x + 1)
                WEST -> currentState.position.copy(x = currentState.position.x - 1)
                SOUTH -> currentState.position.copy(y = currentState.position.y - 1)
                NORTH -> currentState.position.copy(y = currentState.position.y + 1)
            }
    }

    data object RotateLeft : Command {
        override fun nextDirection(currentDirection: Direction): Direction =
            when (currentDirection) {
                WEST -> SOUTH
                SOUTH -> EAST
                EAST -> NORTH
                NORTH -> WEST
            }

        override fun nextPosition(currentState: RoverState): Position =
            currentState.position
    }

    data object RotateRight : Command {
        override fun nextDirection(currentDirection: Direction): Direction =
            when (currentDirection) {
                NORTH -> EAST
                EAST -> SOUTH
                SOUTH -> WEST
                WEST -> NORTH
            }

        override fun nextPosition(currentState: RoverState): Position =
            currentState.position
    }
}

enum class Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST,
}