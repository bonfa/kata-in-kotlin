package it.fbonfadelli.playground.marsrover

import it.fbonfadelli.playground.marsrover.Direction.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MarsRoverTest {

    // MOVE FORWARD
    @Test
    fun `move forward once`() {
        val rover = Rover(state = RoverState(NORTH, Position(0, 0)), commands = listOf(MoveForward))

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(NORTH, Position(0, 1)))
    }

    @Test
    fun `move forward twice`() {
        val rover = Rover(state = RoverState(NORTH, Position(0, 0)), commands = listOf(MoveForward, MoveForward))

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(NORTH, Position(0, 2)))
    }

    @Test
    fun `different initial position - move forward twice`() {
        val rover = Rover(state = RoverState(NORTH, Position(0, 2)), commands = listOf(MoveForward, MoveForward))

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(NORTH, Position(0, 4)))
    }

    @Test
    fun `initial direction is SOUTH - move forward`() {
        val rover = Rover(state = RoverState(SOUTH, Position(0, 2)), commands = listOf(MoveForward))

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(SOUTH, Position(0, 1)))
    }

    // ROTATE LEFT
    @Test
    fun `initial direction is NORTH - rotate left`() {
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
    fun `initial direction is SOUTH - rotate left`() {
        val rover = Rover(state = RoverState(SOUTH, Position(0, 2)), commands = listOf(RotateLeft))

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(EAST, Position(0, 2)))
    }

    @Test
    fun `initial direction is EAST - rotate left`() {
        val rover = Rover(state = RoverState(EAST, Position(0, 2)), commands = listOf(RotateLeft))

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(NORTH, Position(0, 2)))
    }

    // ROTATE RIGHT
    @Test
    fun `initial direction is NORTH - rotate right`() {
        val rover = Rover(state = RoverState(NORTH, Position(0, 2)), commands = listOf(RotateRight))

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(EAST, Position(0, 2)))
    }

    @Test
    fun `initial direction is WEST - rotate right`() {
        val rover = Rover(state = RoverState(EAST, Position(0, 2)), commands = listOf(RotateRight))

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(SOUTH, Position(0, 2)))
    }

    @Test
    fun `initial direction is SOUTH - rotate right`() {
        val rover = Rover(state = RoverState(SOUTH, Position(0, 2)), commands = listOf(RotateRight))

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(WEST, Position(0, 2)))
    }

    @Test
    fun `initial direction is EAST, rotate right`() {
        val rover = Rover(state = RoverState(WEST, Position(0, 2)), commands = listOf(RotateRight))

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(NORTH, Position(0, 2)))
    }

    @Test
    fun `initial direction is EAST - rotate right twice`() {
        val rover = Rover(state = RoverState(WEST, Position(0, 2)), commands = listOf(RotateRight, RotateRight))

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(EAST, Position(0, 2)))
    }

    @Test
    fun `initialDirection is SOUTH, a mix of rotations and move forward`() {
        val rover = Rover(
            state = RoverState(SOUTH, Position(0, 2)),
            listOf(MoveForward, RotateRight, RotateRight, MoveForward)
        )

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(NORTH, Position(0, 2)))
    }

    @Test
    fun `initial direction is EAST, move forward`() {
        val rover = Rover(
            state = RoverState(EAST, Position(0, 2)),
            listOf(MoveForward)
        )

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(EAST, Position(1, 2)))
    }

    @Test
    fun `initial direction is WEST, move forward`() {
        val rover = Rover(
            state = RoverState(WEST, Position(0, 2)),
            commands = listOf(MoveForward)
        )

        val finalState = rover.finalState()

        assertThat(finalState).isEqualTo(RoverState(WEST, Position(-1, 2)))
    }
}
