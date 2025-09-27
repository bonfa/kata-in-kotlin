package it.fbonfadelli.playground.codecracker

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CodeCrackerAcceptanceTest {

    private val encryptor = Encryptor()
    private val decryptor = Decryptor()

    private val key = "bcdefghijklmnopqrstuvwxyza"

    @Test
    fun `a message`() {
        val message = "abbbabccb"

        val decrypted = decryptor.decrypt(encryptor.encrypt(message, key), key)

        assertThat(decrypted).isEqualTo(message)
    }

    @Test
    fun `all the alphabet`() {
        val message = "abcdefghijklmnopqrstuvwxyz"

        val decrypted = decryptor.decrypt(encryptor.encrypt(message, key), key)

        assertThat(decrypted).isEqualTo(message)
    }
}
