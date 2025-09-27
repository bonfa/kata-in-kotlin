package it.fbonfadelli.playground.codecracker

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DecryptorTest {

    private val decryptor = Decryptor()
    private val key = "bcdefghijklmnopqrstuvwxyza"

    @Test
    fun `empty message`() {
        val decrypted = decryptor.decrypt("", key)

        assertThat(decrypted).isEqualTo("")
    }

    @Test
    fun `a message of one character`() {
        val decrypted = decryptor.decrypt("b", key)

        assertThat(decrypted).isEqualTo("a")
    }

    @Test
    fun `a message of one character - different character`() {
        val decrypted = decryptor.decrypt("c", key)

        assertThat(decrypted).isEqualTo("b")
    }

    @Test
    fun `a message of two characters`() {
        val decrypted = decryptor.decrypt("bc", key)

        assertThat(decrypted).isEqualTo("ab")
    }

    @Test
    fun `a message of many characters`() {
        val decrypted = decryptor.decrypt("bcccbcddc", key)

        assertThat(decrypted).isEqualTo("abbbabccb")
    }
}
