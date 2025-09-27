package it.fbonfadelli.playground.codecracker

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DecryptorTest {

    private val key = "bcdefghijklmnopqrstuvwxyza"
    private val decryptor = Decryptor(key)

    @Test
    fun `empty message`() {
        val decrypted = decryptor.decrypt("")

        assertThat(decrypted).isEqualTo("")
    }

    @Test
    fun `a message of one character`() {
        val decrypted = decryptor.decrypt("b")

        assertThat(decrypted).isEqualTo("a")
    }

    @Test
    fun `a message of one character - different character`() {
        val decrypted = decryptor.decrypt("c")

        assertThat(decrypted).isEqualTo("b")
    }

    @Test
    fun `a message of two characters`() {
        val decrypted = decryptor.decrypt("bc")

        assertThat(decrypted).isEqualTo("ab")
    }

    @Test
    fun `a message of many characters`() {
        val decrypted = decryptor.decrypt("bcccbcddc")

        assertThat(decrypted).isEqualTo("abbbabccb")
    }
}
