package com.centennialcollege.pizzaease.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.centennialcollege.pizzaease.dao.FoodDao
import com.centennialcollege.pizzaease.model.Food
import com.centennialcollege.pizzaease.model.PizzaSize
import com.centennialcollege.pizzaease.ui.components.ExpandableImage
import com.centennialcollege.pizzaease.ui.components.PizzaSizeSelector
import com.centennialcollege.pizzaease.ui.theme.ubuntuFont
import kotlinx.coroutines.launch

//display details about a specific pizza item, including description and size
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodScreen(navController: NavController, food: Food) {
    val coroutineScope = rememberCoroutineScope()
    var selectedSize by remember { mutableStateOf(PizzaSize.Small) }

    // Scaffold is a layout component that provides app structure, like app bars and navigation drawers
    Scaffold(
        topBar = {
            // CenterAlignedTopAppBar is a custom top app bar with centered title and navigation icon
            CenterAlignedTopAppBar(
                modifier = Modifier.shadow(8.dp),
                navigationIcon = {
                    // Navigation icon to navigate back to the previous screen
                    Row {
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp)
                                .clickable {
                                    navController.popBackStack()
                                }
                        )
                    }
                },
                title = {
                    // Title of the screen
                    Text(text = "Pick your pizza", fontFamily = ubuntuFont)
                },
                actions = {
                    // Action button in the app bar (e.g., add button)
                    IconButton(
                        onClick = {
                            // Navigate to a new screen when the action button is clicked
                            coroutineScope.launch {
                                navController.navigate("new_screen_route")
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { paddings ->
        // Content of the screen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddings)
                .verticalScroll(rememberScrollState()) // Enable vertical scrolling
        ) {
            // Expandable image component for displaying the food image
            ExpandableImage(
                drawableId = food.image,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Column for other food details
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = food.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))

                // Selector for choosing pizza size
                PizzaSizeSelector(
                    sizes = PizzaSize.values().toList(),
                    onSizeSelected = { size ->
                        selectedSize = size // Update selectedSize when a size is selected
                    },
                    selectedSize = selectedSize // Pass the selected size to the selector
                )
                // Display price based on the selected size
                Text(
                    text = "CAD$ ${food.sizePriceMap[selectedSize]}",
                    fontSize = 17.sp
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            // Divider for visual separation
            Divider(thickness = 2.dp)
            Spacer(modifier = Modifier.height(16.dp))

            // Description of the food
            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                Text(
                    text = "Description",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Display food description with justified alignment
                Text(
                    text = "${food.description}",
                    fontSize = 13.sp,
                    textAlign = TextAlign.Justify,
                    color = Color(0xff313131)
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Button to add the selected food to the order
                Button(
                    onClick = {
                        // Navigate to the order screen with selected food details
                        coroutineScope.launch {
                            navController.navigate("order/${food.name}/${food.sizePriceMap[selectedSize]}/${selectedSize}")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Add", fontSize = 16.sp)
                }
            }

            // Divider for visual separation
            Divider(thickness = 2.dp)
            Spacer(modifier = Modifier.height(16.dp))

            // Recommended foods section
            Text(
                text = "Other Recommendations",
                modifier = Modifier.padding(horizontal = 8.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Horizontal list of recommended foods
            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                items(FoodDao.getAllFood()) { food ->
                    RecommendedFood(food = food, onTap = { food ->
                        navController.navigate("food/${food.id}")
                    })
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendedFood(food: Food, onTap: (Food) -> Unit) {
    // Card composable for displaying other recommended item
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xfff1f1f1)
        ),
        onClick = {
            onTap(food)
        }
    ) {
        Column {
            // Image composable for displaying the food image. connected to model and dao

            Image(
                modifier = Modifier.sizeIn(
                    maxWidth = 120.dp,
                    maxHeight = 70.dp
                ),
                painter = painterResource(id = food.image),
                contentDescription = food.name,
                contentScale = ContentScale.Crop
            )
            // Column for additional details below the image

            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                Spacer(modifier = Modifier.height(8.dp))
                //display food name

                Text(text = food.name, color = Color(0xff313131), fontSize = 13.sp)
                Spacer(modifier = Modifier.height(4.dp))
                // display price information
                Text(text = "from ${food.sizePriceMap[PizzaSize.Small]}$")
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}
