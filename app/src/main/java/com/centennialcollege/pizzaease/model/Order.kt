package com.centennialcollege.pizzaease.model

data class Order(
    val id: String,
    val pizzas: List<OrderItem>,
    val deliveryAddress: String,
    val customerName: String,
    val customerPhone: String,
    val orderTime: Long
)
