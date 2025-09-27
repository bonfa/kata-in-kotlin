package it.fbonfadelli.playground.codecracker

class Decryptor(private val key: String) {
    fun decrypt(message: String): String =
        message
            .map { char -> ('a'.code + key.indexOf(char)).toChar() }
            .joinToString("")
}