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
        val rover = Rover(state = RoverState(0, NORTH), commands = listOf(MoveForward))

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(1, NORTH))
    }

    @Test
    fun `1d space - move forward twice`() {
        val rover = Rover(state = RoverState(0, NORTH), commands = listOf(MoveForward, MoveForward))

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(2, NORTH))
    }

    @Test
    fun `1d space - different initial position - move forward twice`() {
        val rover = Rover(state = RoverState(2, NORTH), commands = listOf(MoveForward, MoveForward))

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(4, NORTH))
    }

    @Test
    fun `1d space - initial direction is SOUTH - move forward`() {
        val rover = Rover(state = RoverState(2, SOUTH), commands = listOf(MoveForward))

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(1, SOUTH))
    }

    // ROTATE LEFT
    @Test
    fun `1d space - initial direction is NORTH - rotate left`() {
        val rover = Rover(state = RoverState(2, NORTH), commands = listOf(RotateLeft))

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(2, WEST))
    }

    @Test
    fun `1d space- - initial direction is WEST - rotate left`() {
        val rover = Rover(state = RoverState(2, WEST), commands = listOf(RotateLeft))

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(2, SOUTH))
    }

    @Test
    fun `1d space - initial direction is SOUTH - rotate left`() {
        val rover = Rover(state = RoverState(2, SOUTH), commands = listOf(RotateLeft))

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(2, EAST))
    }

    @Test
    fun `1d space - initial direction is EAST - rotate left`() {
        val rover = Rover(state = RoverState(2, EAST), commands = listOf(RotateLeft))

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(2, NORTH))
    }

    // ROTATE RIGHT
    @Test
    fun `1d space - initial direction is NORTH - rotate right`() {
        val rover = Rover(state = RoverState(2, NORTH), commands = listOf(RotateRight))

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(2, EAST))
    }

    @Test
    fun `1d space - initial direction is WEST - rotate right`() {
        val rover = Rover(state = RoverState(2, EAST), commands = listOf(RotateRight))

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(2, SOUTH))
    }

    @Test
    fun `1d space - initial direction is SOUTH - rotate right`() {
        val rover = Rover(state = RoverState(2, SOUTH), commands = listOf(RotateRight))

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(2, WEST))
    }

    @Test
    fun `1d space - initial direction is EAST, rotate right`() {
        val rover = Rover(state = RoverState(2, WEST), commands = listOf(RotateRight))

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(2, NORTH))
    }

    @Test
    fun `1d space - initial direction is EAST - rotate right twice`() {
        val rover = Rover(state = RoverState(2, WEST), commands = listOf(RotateRight, RotateRight))

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(2, EAST))
    }

    @Test
    fun `1d space, initialDirection is SOUTH, a mix of rotations and moveForward`() {
        val rover = Rover(
            state = RoverState(2, SOUTH),
            listOf(MoveForward, RotateRight, RotateRight, MoveForward)
        )

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(2, NORTH))
    }

    @Test
    fun `2d space`() {
        val rover = Rover(
            state = RoverState(2, EAST, 0),
            listOf(MoveForward)
        )

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(2, EAST, 1))
    }
}

data class RoverState(
    val position: Int, //Y
    val direction: Direction,
    val x: Int = 0,
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
    fun nextPosition(currentState: RoverState): RoverState

    fun nextState(currentState: RoverState): RoverState {
        val nextPosition = nextPosition(currentState)
        return RoverState(
            position = nextPosition.position,
            direction = nextDirection(currentState.direction),
            x = nextPosition.x,
        )
    }

    data object MoveForward : Command {
        override fun nextDirection(currentDirection: Direction): Direction =
            currentDirection

        override fun nextPosition(currentState: RoverState): RoverState =
            if (currentState.direction == EAST)
                currentState.copy(x = currentState.x + 1)
            else if (currentState.direction == SOUTH)
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