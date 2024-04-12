package com.centennialcollege.pizzaease.model

import androidx.annotation.DrawableRes
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Food(
    val id : UUID,
    val name: String,
    @DrawableRes val image: Int,
    val type: FoodType,
    val liked: Boolean = false,
    val sizePriceMap: Map<PizzaSize, Double>,
)

@Serializable
enum class PizzaSize(val size: String) {
    Small("10\""),
    Medium("12\""),
    Large("14\""),
}
@Serializable
enum class FoodType {
    Pizza
}

