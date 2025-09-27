package it.fbonfadelli.playground.codecracker

class CodeCrackerTest {

    /*

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
}
