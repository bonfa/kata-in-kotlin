package it.fbonfadelli.playground.marsrover

sealed interface Command {
    fun nextState(currentState: State): State
}

data object MoveForward : Command {
    override fun nextState(currentState: State): State =
        currentState.copy(
            position = nextPosition(currentState),
        )

    private fun nextPosition(currentState: State): Position =
        when (currentState.direction) {
            Direction.EAST -> currentState.position.copy(x = currentState.position.x + 1)
            Direction.WEST -> currentState.position.copy(x = currentState.position.x - 1)
            Direction.SOUTH -> currentState.position.copy(y = currentState.position.y - 1)
            Direction.NORTH -> currentState.position.copy(y = currentState.position.y + 1)
        }
}

data object RotateLeft : Command {
    override fun nextState(currentState: State): State =
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
    override fun nextState(currentState: State): State =
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