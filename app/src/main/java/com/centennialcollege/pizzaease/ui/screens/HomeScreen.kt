package com.centennialcollege.pizzaease.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.centennialcollege.pizzaease.R
import com.centennialcollege.pizzaease.dao.FoodDao
import com.centennialcollege.pizzaease.model.Food
import com.centennialcollege.pizzaease.model.FoodType
import com.centennialcollege.pizzaease.model.PizzaSize
import com.centennialcollege.pizzaease.ui.components.TabLayout
import com.centennialcollege.pizzaease.ui.theme.ubuntuFont
import com.google.accompanist.systemuicontroller.rememberSystemUiController

//tabbed layout for different food items with card views, also homescreen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val uiController = rememberSystemUiController()
    uiController.isStatusBarVisible = false

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(stringResource(id = R.string.brand_slogan), fontFamily = ubuntuFont)
            },
            navigationIcon = {
                Row {
                    Spacer(modifier = Modifier.width(8.dp))
                   // Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
                }
            }
        )
    }) { paddings ->
        Column(modifier = Modifier.padding(paddings)) {
            val selectedFoodType = remember {
                mutableIntStateOf(0)
            }
            val foods = FoodDao.getAllFood()
            val foodsState = remember {
                mutableStateListOf(*(foods).toTypedArray())
            }
            val onLikeChange: (Food) -> Unit = {
                foodsState[foodsState.indexOf(it)] =
                    foodsState[foodsState.indexOf(it)].copy(liked = !it.liked)
            }
            Spacer(modifier = Modifier.height(16.dp))
            TabLayout(
                items = listOf(
                    "Pizza" to {
                        Foods(
                            items = foodsState.filter { it.type == FoodType.Pizza },
                            onLikeChange = onLikeChange,
                            onTap = {
                                navController.navigate("food/${it.id}")
                            }
                        )
                    },
                ),
                selectedIndex = selectedFoodType.intValue,
                onTabClick = {
                    selectedFoodType.intValue = it
                },
                textHeight = 30.dp,
                indicatorPadding = PaddingValues(horizontal = 10.dp)
            )
        }
    }
}
//display pizza products in card view
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Foods(items: List<Food>, onLikeChange: (Food) -> Unit, onTap: (Food) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xfff1f1f1))
            .padding(horizontal = 8.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(items) { index, food ->
                Card(
                    onClick = {
                        onTap(food)
                    },
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        Image(
                            modifier = Modifier
                                .size(25.dp)
                                .clickable {
                                    onLikeChange(food)
                                },
                            painter = painterResource(
                                id =
                                if (food.liked) R.drawable.ic_like else R.drawable.ic_unlike
                            ),
                            contentDescription = null
                        )
                    }
                    Column(modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Image(
                            modifier= Modifier
                                .size(100.dp)
                                .clip(CircleShape),
                            painter = painterResource(id = food.image),
                            contentDescription = food.name,
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = food.name, fontSize = 15.sp, color = Color(0xff383838))
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(text = "from ${food.sizePriceMap[PizzaSize.Small]}$")
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    }
}