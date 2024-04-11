package com.centennialcollege.pizzaease.dao

import com.centennialcollege.pizzaease.R
import com.centennialcollege.pizzaease.model.Food
import com.centennialcollege.pizzaease.model.FoodType
import com.centennialcollege.pizzaease.model.OrderItem
import java.util.UUID

object FoodDao {

    private val foods = listOf(
        Food(
            id = UUID.fromString("c5f0c3cb-98dc-4efd-99a2-18103a831c9a"),
            name = "Cheese Pizza",
            image = R.drawable.pizza_cheese,
            type = FoodType.Pizza
        ),
        Food(
            id = UUID.fromString("9279aee9-3b6a-4b50-8760-c66611ee408f"),
            name = "Hawaiian Pizza",
            image = R.drawable.pizza_hawaiian,
            type = FoodType.Pizza
        ),
        Food(
            id = UUID.fromString("fe1b3e33-beb1-45c4-8a45-542ec993d067"),
            name = "Italian Pizza",
            image = R.drawable.pizza_italian,
            type = FoodType.Pizza
        ),
        Food(
            id = UUID.fromString("2761baa4-5865-4378-8871-43cd6691c82c"),
            name = "Margherita Pizza",
            image = R.drawable.pizza_margherita,
            type = FoodType.Pizza
        ),
        Food(
            id = UUID.fromString("6a593bf5-d3f9-48be-9072-8f5303304f00"),
            name = "Pepperoni Pizza",
            image = R.drawable.pizza_pepperoni,
            type = FoodType.Pizza
        ),
        Food(
            id = UUID.fromString("3c3efff6-7685-467e-9c37-75ed7296910c"),
            name = "Tomato Basil Pizza",
            image = R.drawable.pizza_tomato_basil,
            type = FoodType.Pizza
        ),
    )
    fun getFood(id: UUID): Food? {
        return foods.find { it.id == id }
    }

    fun getFood(id: UUID, callback: (Food?) -> Unit) {
        callback(getFood(id))
    }

    fun getAllFood():List<Food> {
        return foods
    }

    fun getAllFood(callback: (List<Food>) -> Unit) {
        callback(foods)
    }
}