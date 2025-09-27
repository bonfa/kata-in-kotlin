package it.fbonfadelli.playground.codecracker

class Decryptor {
    fun decrypt(message: String, key: String): String =
        message
            .map { char -> ('a'.code + key.indexOf(char)).toChar() }
            .joinToString("")
}