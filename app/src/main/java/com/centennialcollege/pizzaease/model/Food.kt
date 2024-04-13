package com.centennialcollege.pizzaease.model

import androidx.annotation.DrawableRes
import kotlinx.serialization.Serializable
import java.util.UUID

//define and serialize pizza and sizes
@Serializable
data class Food(
    val id : UUID, //unique id for pizza item
    val name: String,
    val description: String,
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

