package it.fbonfadelli.playground.marsrover

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import sun.security.util.Length
import javax.swing.text.Position

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
     - map: 1D, 1 command (turn right)
     - map: 1D, 1 command (turn left)
     - map: 1D, 2 commands (forward, forward)
     - map: 1D, 2 commands (forward, turn right)
     - map: 1D, 2 commands (forward, turn left)
     - map: 2D, 1 command (turn right)
     */

    @Test
    fun `1d space, move forward`() {
        val rover = Rover(0, 0,10, 0)

        val finalPosition = rover.finalPosition()

        assertThat(finalPosition).isEqualTo(1)
    }
}

class Rover(initialPosition: Int, initialDirection:Int, spaceLength: Int, command: Int) {
    fun finalPosition(): Int {
        return 1
    }
}
