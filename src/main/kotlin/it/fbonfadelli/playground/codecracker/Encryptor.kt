package it.fbonfadelli.playground.codecracker

class Encryptor {
    fun encrypt(message: String, key: String): String =
        message.map { key[it - 'a'] }.joinToString("")
}