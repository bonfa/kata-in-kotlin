package it.fbonfadelli.playground.inventory

data class Product(
    val sku: Sku,
    val name: String,
    val quantity: Int,
    val singleProductPriceInEurDecimals: Int,
)

data class Sku(val value: String)