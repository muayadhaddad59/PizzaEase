package com.centennialcollege.pizzaease.model


data class OrderItem(
    val id: String,
    val itemName: String,
    val itemType: String,
    val size: String,
    val toppings: List<String>,
    val crustType: String,
    val quantity: Int
)