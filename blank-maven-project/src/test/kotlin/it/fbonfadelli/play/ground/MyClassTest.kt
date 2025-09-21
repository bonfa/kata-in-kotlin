package it.fbonfadelli.play.ground

import org.junit.jupiter.api.Assertions.*

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MyClassTest {

    @Test
    fun `my first test`() {
        assertThat(true).isFalse()
    }
}