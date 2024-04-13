package com.centennialcollege.pizzaease.dao

import com.centennialcollege.pizzaease.R
import com.centennialcollege.pizzaease.model.Food
import com.centennialcollege.pizzaease.model.FoodType
import com.centennialcollege.pizzaease.model.PizzaSize
import java.util.UUID

//list of available pizza and their details including images
object FoodDao {

    private val foods = listOf(
        Food(
            id = UUID.fromString("c5f0c3cb-98dc-4efd-99a2-18103a831c9a"),
            name = "Cheese Pizza",
            description = "A classic pizza topped with melted mozzarella cheese over a flavorful tomato sauce on a crispy crust.",
            image = R.drawable.pizza_cheese,
            type = FoodType.Pizza,
            sizePriceMap = mapOf(
                PizzaSize.Small to 10.0,
                PizzaSize.Medium to 12.0,
                PizzaSize.Large to 16.0,
            )
        ),
        Food(
            id = UUID.fromString("9279aee9-3b6a-4b50-8760-c66611ee408f"),
            name = "Hawaiian Pizza",
            description = "A delightful combination of juicy pineapple, savory ham, and gooey mozzarella cheese on a delicious pizza crust.",
            image = R.drawable.pizza_hawaiian,
            type = FoodType.Pizza,
            sizePriceMap = mapOf(
                PizzaSize.Small to 13.0,
                PizzaSize.Medium to 16.0,
                PizzaSize.Large to 22.0,
            )
        ),
        Food(
            id = UUID.fromString("fe1b3e33-beb1-45c4-8a45-542ec993d067"),
            name = "Italian Pizza",
            description = "An authentic Italian-style pizza featuring rich tomato sauce, fresh basil, flavorful Italian herbs, and melted cheese.\n",
            image = R.drawable.pizza_italian,
            type = FoodType.Pizza,
            sizePriceMap = mapOf(
                PizzaSize.Small to 13.0,
                PizzaSize.Medium to 16.0,
                PizzaSize.Large to 22.0,
            )
        ),
        Food(
            id = UUID.fromString("2761baa4-5865-4378-8871-43cd6691c82c"),
            name = "Margherita Pizza",
            description = "A traditional Italian pizza topped with fresh tomatoes, fragrant basil leaves, and creamy mozzarella cheese for a simple yet delicious flavor.\n",
            image = R.drawable.pizza_margherita,
            type = FoodType.Pizza,
            sizePriceMap = mapOf(
                PizzaSize.Small to 16.0,
                PizzaSize.Medium to 19.0,
                PizzaSize.Large to 23.0,
            )
        ),
        Food(
            id = UUID.fromString("6a593bf5-d3f9-48be-9072-8f5303304f00"),
            name = "Pepperoni Pizza",
            description = "A popular choice with zesty pepperoni slices, melted mozzarella cheese, and tangy tomato sauce on a golden-brown crust.\n",
            image = R.drawable.pizza_pepperoni,
            type = FoodType.Pizza,
            sizePriceMap = mapOf(
                PizzaSize.Small to 11.0,
                PizzaSize.Medium to 13.0,
                PizzaSize.Large to 17.0,
            )
        ),
        Food(
            id = UUID.fromString("3c3efff6-7685-467e-9c37-75ed7296910c"),
            name = "Tomato Basil Pizza",
            description = "A light and flavorful pizza highlighting ripe tomatoes, aromatic basil, and a hint of garlic on a thin, crispy crust, perfect for a refreshing taste.\n",
            image = R.drawable.pizza_tomato_basil,
            type = FoodType.Pizza,
            sizePriceMap = mapOf(
                PizzaSize.Small to 10.0,
                PizzaSize.Medium to 12.0,
                PizzaSize.Large to 16.0,
            )
        ),
    )
    // Function to get a specific food item by its ID

    fun getFood(id: UUID): Food? {
        return foods.find { it.id == id }
    }

    fun getFood(id: UUID, callback: (Food?) -> Unit) {
        callback(getFood(id))
    }
    // Function to get all available food items

    fun getAllFood():List<Food> {
        return foods
    }

    fun getAllFood(callback: (List<Food>) -> Unit) {
        callback(foods)
    }
}