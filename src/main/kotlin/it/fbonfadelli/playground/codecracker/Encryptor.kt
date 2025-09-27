package it.fbonfadelli.playground.codecracker

class Encryptor(private val key: String) {
    fun encrypt(message: String): String =
        message.map { key[it - 'a'] }.joinToString("")
}