package it.fbonfadelli.playground.codecracker

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class EncryptorTest {

    @Test
    fun `empty message`() {
        val encryptor = Encryptor()
        val key = "bcdefghijklmnopqrstuvwxyza"
        assertThat(encryptor.encrypt("", key)).isEqualTo("")
    }

    @Test
    fun `a message with one character`() {
        val encryptor = Encryptor()
        val key = "bcdefghijklmnopqrstuvwxyza"
        assertThat(encryptor.encrypt("a", key)).isEqualTo("b")
    }

    @Test
    fun `another message with one character`() {
        val encryptor = Encryptor()
        val key = "bcdefghijklmnopqrstuvwxyza"
        assertThat(encryptor.encrypt("b", key)).isEqualTo("c")
    }

    @Test
    fun `a message with one character - different key`() {
        val encryptor = Encryptor()
        val key = "cdefghijklmnopqrstuvwxyzab"
        assertThat(encryptor.encrypt("a", key)).isEqualTo("c")
    }

    @Test
    fun `2 characters`() {
        val encryptor = Encryptor()
        val key = "bcdefghijklmnopqrstuvwxyza"
        assertThat(encryptor.encrypt("aa", key)).isEqualTo("bb")
    }

    @Test
    fun `many characters`() {
        val encryptor = Encryptor()
        val key = "bcdefghijklmnopqrstuvwxyza"
        assertThat(encryptor.encrypt("abbbabccb", key)).isEqualTo("bcccbcddc")
    }
}
