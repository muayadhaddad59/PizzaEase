package ir.ehsan.asmrfooddelivery.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.centennialcollege.pizzaease.dao.FoodDao
import com.centennialcollege.pizzaease.model.Food
import com.centennialcollege.pizzaease.ui.screens.AuthScreen
import com.centennialcollege.pizzaease.ui.screens.OrderScreen
import com.centennialcollege.pizzaease.ui.screens.FoodScreen
import com.centennialcollege.pizzaease.ui.screens.HomeScreen
import java.util.UUID

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("main", Context.MODE_PRIVATE)
    val userLoggedIn = sharedPreferences.getBoolean("loggedIn",false)
    NavHost(navController = navController, startDestination = if (userLoggedIn) "home" else "auth"){
        composable("auth"){
            AuthScreen(navController = navController)
        }
        composable("home"){
            HomeScreen(navController = navController)
        }
        composable("order/{foodName}/{foodPrice}/{foodSize}") { backStackEntry ->
            val foodName = backStackEntry.arguments?.getString("foodName")
            val foodPrice = backStackEntry.arguments?.getString("foodPrice")
            val foodSize = backStackEntry.arguments?.getString("foodSize")

            OrderScreen(
                navController = navController,
                foodName = foodName.orEmpty(),
                foodPrice = foodPrice.orEmpty(),
                foodSize = foodSize.orEmpty()
            )
        }
        composable(
            route = "food/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ){ backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            val food = FoodDao.getFood(UUID.fromString(id))
            food?.let { // Check if food is not null
                val food: Food = it // Safe cast to non-nullable Food
                FoodScreen(navController = navController, food = food)
                println("Food name: ${food.name}")
                println("Food type: ${food.type}")
                // Access other properties of the non-null food object as needed
            } ?: run {
                println("Food not found")
            }
        }
    }
}