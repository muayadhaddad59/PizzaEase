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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodScreen(navController: NavController, food: Food) {
    val coroutineScope = rememberCoroutineScope()
    var selectedSize by remember { mutableStateOf(PizzaSize.Small) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.shadow(8.dp),
                navigationIcon = {
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
                    Text(text = "Food", fontFamily = ubuntuFont)
                },
                actions = {
                    IconButton(
                        onClick = {
                            // Navigate to the new screen
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddings)
                .verticalScroll(rememberScrollState())
        ) {
            ExpandableImage(
                drawableId = food.image,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = food.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                PizzaSizeSelector(
                    sizes = PizzaSize.values().toList(),
                    onSizeSelected = { size ->
                        selectedSize = size
                    },
                    selectedSize = selectedSize
                )
                Text(
                    text = "CAD$ ${food.sizePriceMap[selectedSize]}",
                    fontSize = 17.sp
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Divider(thickness = 2.dp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Recommended Foods:",
                modifier = Modifier.padding(horizontal = 8.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                items(FoodDao.getAllFood()) { food ->
                    RecommendedFood(food = food, onTap = { food ->
                        navController.navigate("food/${food.id}")
                    })
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Divider(thickness = 2.dp)
            Spacer(modifier = Modifier.height(16.dp))
            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                Text(
                    text = "Rating & Reviews",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content. Lorem ipsum may be used as a placeholder before final copy is available.",
                    fontSize = 13.sp,
                    textAlign = TextAlign.Justify,
                    color = Color(0xff313131)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        // Perform action when the button is clicked
                        // For example, you can navigate to a new screen
                        coroutineScope.launch {
                            navController.navigate("order/${food.name}/${food.sizePriceMap[selectedSize]}/${food.sizePriceMap[selectedSize]}")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Add", fontSize = 16.sp)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendedFood(food: Food, onTap: (Food) -> Unit) {
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
            Image(
                modifier = Modifier.sizeIn(
                    maxWidth = 120.dp,
                    maxHeight = 70.dp
                ),
                painter = painterResource(id = food.image),
                contentDescription = food.name,
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = food.name, color = Color(0xff313131), fontSize = 13.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "from ${food.sizePriceMap[PizzaSize.Small]}$")
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}
