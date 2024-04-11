package com.centennialcollege.pizzaease.model

import androidx.annotation.DrawableRes
import com.centennialcollege.pizzaease.R
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Food(
    val id : UUID,
    val name: String,
    @DrawableRes val image: Int,
    val type: FoodType,
    val liked: Boolean = false,
    val price: Int = (10..100).random()
)

@Serializable
enum class FoodType {
    Pizza
}
