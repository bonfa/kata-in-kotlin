package it.fbonfadelli.playground.marsrover

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
    fun nextState(currentState: RoverState): RoverState
}

data object MoveForward : Command {
    override fun nextState(currentState: RoverState): RoverState =
        currentState.copy(
            position = nextPosition(currentState),
        )

    private fun nextPosition(currentState: RoverState): Position =
        when (currentState.direction) {
            Direction.EAST -> currentState.position.copy(x = currentState.position.x + 1)
            Direction.WEST -> currentState.position.copy(x = currentState.position.x - 1)
            Direction.SOUTH -> currentState.position.copy(y = currentState.position.y - 1)
            Direction.NORTH -> currentState.position.copy(y = currentState.position.y + 1)
        }
}

data object RotateLeft : Command {
    override fun nextState(currentState: RoverState): RoverState =
        currentState.copy(
            direction = nextDirection(currentState.direction),
        )

    private fun nextDirection(currentDirection: Direction): Direction =
        when (currentDirection) {
            Direction.WEST -> Direction.SOUTH
            Direction.SOUTH -> Direction.EAST
            Direction.EAST -> Direction.NORTH
            Direction.NORTH -> Direction.WEST
        }
}

data object RotateRight : Command {
    override fun nextState(currentState: RoverState): RoverState =
        currentState.copy(
            direction = nextDirection(currentState.direction),
        )

    private fun nextDirection(currentDirection: Direction): Direction =
        when (currentDirection) {
            Direction.NORTH -> Direction.EAST
            Direction.EAST -> Direction.SOUTH
            Direction.SOUTH -> Direction.WEST
            Direction.WEST -> Direction.NORTH
        }
}

enum class Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST,
}