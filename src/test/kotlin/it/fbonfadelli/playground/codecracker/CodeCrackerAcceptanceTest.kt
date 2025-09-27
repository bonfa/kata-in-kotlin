package it.fbonfadelli.playground.codecracker

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CodeCrackerAcceptanceTest {

    private val key = "!)\"(£*%&><@abcdefghijklmno"
    private val encryptor = Encryptor(key)
    private val decryptor = Decryptor(key)

    @Test
    fun `a message`() {
        val message = "abbbabccb"

        val decrypted = decryptor.decrypt(encryptor.encrypt(message))

        assertThat(decrypted).isEqualTo(message)
    }

    @Test
    fun `all the alphabet`() {
        val message = "abcdefghijklmnopqrstuvwxyz"

        val decrypted = decryptor.decrypt(encryptor.encrypt(message))

        assertThat(decrypted).isEqualTo(message)
    }
}
