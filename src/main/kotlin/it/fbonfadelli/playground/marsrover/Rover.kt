package it.fbonfadelli.playground.marsrover

class Rover(
    private val state: State,
    private val commands: List<Command>
) {
    fun finalState(): State =
        commands.fold(state) { currentState, command ->
            command.nextState(currentState)
        }
}

data class State(
    val direction: Direction,
    val position: Position,
)

data class Position(
    val x: Int,
    val y: Int
)

enum class Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST,
}