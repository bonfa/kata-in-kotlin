package it.fbonfadelli.playground.codecracker

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class EncryptorTest {

    private val encryptor = Encryptor()
    private val key = "bcdefghijklmnopqrstuvwxyza"

    @Test
    fun `empty message`() {
        val encrypted = encryptor.encrypt("", key)

        assertThat(encrypted).isEqualTo("")
    }

    @Test
    fun `a message with one character`() {
        val encrypted = encryptor.encrypt("a", key)

        assertThat(encrypted).isEqualTo("b")
    }

    @Test
    fun `another message with one character`() {
        val encrypted = encryptor.encrypt("b", key)

        assertThat(encrypted).isEqualTo("c")
    }

    @Test
    fun `2 characters`() {
        val encrypted = encryptor.encrypt("aa", key)

        assertThat(encrypted).isEqualTo("bb")
    }

    @Test
    fun `many characters`() {
        val encrypted = encryptor.encrypt("abbbabccb", key)

        assertThat(encrypted).isEqualTo("bcccbcddc")
    }
}
