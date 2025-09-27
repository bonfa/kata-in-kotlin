package it.fbonfadelli.playground.codecracker

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class EncryptorTest {

    private val key = "bcdefghijklmnopqrstuvwxyza"
    private val encryptor = Encryptor(key)

    @Test
    fun `empty message`() {
        val encrypted = encryptor.encrypt("")

        assertThat(encrypted).isEqualTo("")
    }

    @Test
    fun `a message with one character`() {
        val encrypted = encryptor.encrypt("a")

        assertThat(encrypted).isEqualTo("b")
    }

    @Test
    fun `another message with one character`() {
        val encrypted = encryptor.encrypt("b")

        assertThat(encrypted).isEqualTo("c")
    }

    @Test
    fun `2 characters`() {
        val encrypted = encryptor.encrypt("aa")

        assertThat(encrypted).isEqualTo("bb")
    }

    @Test
    fun `many characters`() {
        val encrypted = encryptor.encrypt("abbbabccb")

        assertThat(encrypted).isEqualTo("bcccbcddc")
    }
}
