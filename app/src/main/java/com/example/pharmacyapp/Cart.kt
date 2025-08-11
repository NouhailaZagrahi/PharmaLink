package com.example.pharmacyapp

data class Cart(
    val idCart: Int,
    val medicId: Int,
    val medicTitle: String,
    val medicImage: Int,
    var quantity: Int,
    val priceUnite: Int
)
