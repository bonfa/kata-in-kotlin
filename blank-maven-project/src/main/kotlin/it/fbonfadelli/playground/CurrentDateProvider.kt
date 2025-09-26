package it.fbonfadelli.playground

import java.time.LocalDate

interface CurrentDateProvider {
    fun get(): LocalDate
}