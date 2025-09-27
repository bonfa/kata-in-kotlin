package it.fbonfadelli.playground.codecracker

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class CodeCrackerTest {

    /*

    encryptor - (message, key) --> encrypted message
        - "", any() -> ""
        - "a", "b c d e f g h i j k l m n o p q r s t u v w x y z a" -> "b"
        - "b", "b c d e f g h i j k l m n o p q r s t u v w x y z a" -> "c"
        - "ab", "b c d e f g h i j k l m n o p q r s t u v w x y z a" -> "bc"
        - "abbbabccb", "b c d e f g h i j k l m n o p q r s t u v w x y z a" -> "bcccbcddc"

    decryptor - (message, key) --> decrypted message
        - "", any() -> ""
        - "b", "b c d e f g h i j k l m n o p q r s t u v w x y z a" -> "a"
        - "c", "b c d e f g h i j k l m n o p q r s t u v w x y z a" -> "b"
        - "bc", "b c d e f g h i j k l m n o p q r s t u v w x y z a" -> "ab"
        - "bcccbcddc", "b c d e f g h i j k l m n o p q r s t u v w x y z a" -> "abbbabccb"

    acceptance
        - "a", any() -> [encrypt-decrypt] -> "a"
        - "b", any() -> [encrypt-decrypt] -> "b"
        - "ab", any() -> [encrypt-decrypt] -> "ab"
        - "abbbabccb", any() -> [encrypt-decrypt] -> "abbbabccb"
     */

    @Test
    fun `encryptor - empty message`() {
        val encryptor = Encryptor()
        val key = "bcdefghijklmnopqrstuvwxyza"
        assertThat(encryptor.encrypt("", key)).isEqualTo("")
    }

    @Test
    fun `encryptor - a message with one character`() {
        val encryptor = Encryptor()
        val key = "bcdefghijklmnopqrstuvwxyza"
        assertThat(encryptor.encrypt("a", key)).isEqualTo("b")
    }

    @Test
    fun `encryptor - another message with one character`() {
        val encryptor = Encryptor()
        val key = "bcdefghijklmnopqrstuvwxyza"
        assertThat(encryptor.encrypt("b", key)).isEqualTo("c")
    }

    @Test
    fun `encryptor - a message with one character - different key`() {
        val encryptor = Encryptor()
        val key = "cdefghijklmnopqrstuvwxyzab"
        assertThat(encryptor.encrypt("a", key)).isEqualTo("c")
    }

    @Test
    fun `encryptor - 2 characters`() {
        val encryptor = Encryptor()
        val key = "bcdefghijklmnopqrstuvwxyza"
        assertThat(encryptor.encrypt("aa", key)).isEqualTo("bb")
    }

    @Test
    fun `encryptor - many characters`() {
        val encryptor = Encryptor()
        val key = "bcdefghijklmnopqrstuvwxyza"
        assertThat(encryptor.encrypt("abbbabccb", key)).isEqualTo("bcccbcddc")
    }
}

class Encryptor {
    fun encrypt(message: String, key: String): String =
        message.map { key[it - 'a'] }.joinToString("")
}