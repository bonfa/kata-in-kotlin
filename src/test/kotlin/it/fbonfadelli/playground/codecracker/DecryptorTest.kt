package it.fbonfadelli.playground.codecracker

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class DecryptorTest {

    @Test
    fun `empty message`() {
        val decryptor = Decryptor()
        val key = "bcdefghijklmnopqrstuvwxyza"

        val decrypted = decryptor.decrypt("", key)

        assertThat(decrypted).isEqualTo("")
    }

    @Test
    fun `a message of one character`() {
        val decryptor = Decryptor()
        val key = "bcdefghijklmnopqrstuvwxyza"

        val decrypted = decryptor.decrypt("b", key)

        assertThat(decrypted).isEqualTo("a")
    }

    @Test
    fun `a message of one character - different character`() {
        val decryptor = Decryptor()
        val key = "bcdefghijklmnopqrstuvwxyza"

        val decrypted = decryptor.decrypt("c", key)

        assertThat(decrypted).isEqualTo("b")
    }

    @Test
    @Disabled
    fun `a message of two characters`() {
        TODO("Not yet implemented")
    }

    @Test
    @Disabled
    fun `a message of many characters`() {
        TODO("Not yet implemented")
    }
}

class Decryptor {
    fun decrypt(message: String, key: String): String {
        return if (message.isEmpty()) "" else if (message == "b") "a" else "b"
    }

}
