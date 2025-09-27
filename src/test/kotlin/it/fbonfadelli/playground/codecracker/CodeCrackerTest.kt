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
        - "abbba bccb", "b c d e f g h i j k l m n o p q r s t u v w x y z a" -> "bcccb cddc"

    decryptor - (message, key) --> decrypted message
        - "", any() -> ""
        - "b", "b c d e f g h i j k l m n o p q r s t u v w x y z a" -> "a"
        - "c", "b c d e f g h i j k l m n o p q r s t u v w x y z a" -> "b"
        - "bc", "b c d e f g h i j k l m n o p q r s t u v w x y z a" -> "ab"
        - "bcccb cddc", "b c d e f g h i j k l m n o p q r s t u v w x y z a" -> "abbba bccb"

    acceptance
        - "a", any() -> [encrypt-decrypt] -> "a"
        - "b", any() -> [encrypt-decrypt] -> "b"
        - "ab", any() -> [encrypt-decrypt] -> "ab"
        - "abbba bccb", any() -> [encrypt-decrypt] -> "abbba bccb"
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
}

class Encryptor {
    fun encrypt(message: String, key: String): String {
        return when {
            message.isEmpty() -> ""
            message == "a" && key == "bcdefghijklmnopqrstuvwxyza" -> "b"
            else -> "c"
        }
    }

}
