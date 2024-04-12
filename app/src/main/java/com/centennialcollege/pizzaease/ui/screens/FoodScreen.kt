package com.centennialcollege.pizzaease.ui.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.centennialcollege.pizzaease.R
import com.centennialcollege.pizzaease.dao.FoodDao
import com.centennialcollege.pizzaease.model.Food
import com.centennialcollege.pizzaease.ui.theme.ubuntuFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodScreen(navController: NavController, food: Food) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier=Modifier.shadow(8.dp),
                navigationIcon = {
                    Row {
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = null,
                            modifier= Modifier
                                .size(30.dp)
                                .clickable {
                                    navController.popBackStack()
                                }
                        )
                    }
                },
                title = {
                    Text(text = "Food", fontFamily = ubuntuFont)
                }
            ) 
        }
    ) { paddings->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddings)
            .verticalScroll(rememberScrollState())) {
            ExpandableImage(
                drawableId = food.image,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(modifier=Modifier.padding(horizontal = 16.dp)) {
                Text(text = food.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "187$", fontSize = 17.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Size", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(2.dp))
                Image(
                    modifier = Modifier
                        .size(50.dp)
                        .clickable {
                            // TODO
                        },
                    painter = painterResource(
                        id = R.drawable.ic_pizza_24
                    ),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Divider(thickness = 2.dp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Recommended Foods:", modifier = Modifier.padding(horizontal = 8.dp),
            fontWeight = FontWeight.Bold, fontSize = 17.sp)
            Spacer(modifier = Modifier.height(16.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)){
                items(FoodDao.getAllFood ()){ food->
                    RecommendedFood(food = food, onTap = {food->
                        navController.navigate("food/${food.id}")
                    })
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Divider(thickness = 2.dp)
            Spacer(modifier = Modifier.height(16.dp))
            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                Text(text = "Rating & Reviews", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content. Lorem ipsum may be used as a placeholder before final copy is available.",
                    fontSize = 13.sp,
                    textAlign = TextAlign.Justify,
                    color = Color(0xff313131)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendedFood(food: Food, onTap:(Food)->Unit) {
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
                modifier=Modifier.sizeIn(
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
                Text(text = "${food.price}$")
                Spacer(modifier = Modifier.height(4.dp))

            }
        }
    }
}

@Composable
fun ExpandableImage(
    drawableId: Int,
    modifier: Modifier = Modifier,
) {
    val expandedState = remember { mutableStateOf(false) }
    val alpha = animateFloatAsState(
        targetValue = if (expandedState.value) 0.5f else 1f,
        animationSpec = tween(durationMillis = 700)
    )
    
    // Measure the original height of the image
    var originalHeight by remember(drawableId) {
        mutableStateOf(500.dp)
    }

    val height = animateDpAsState(
        targetValue = if (expandedState.value) originalHeight else 200.dp, // Adjust the expanded height here
        animationSpec = tween(durationMillis = 500)
    )

    Box(modifier = modifier.height(height.value)) {
        Image(
            painter = painterResource(id = drawableId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .alpha(alpha.value)
                .clickable {
                    expandedState.value = !expandedState.value
                },
        )
    }
}
@Composable
private fun getImageOriginalHeight(drawableId: Int): Dp {
    var height by remember { mutableStateOf(0) }

    // Measure the original height of the image
    Image(
        painter = painterResource(id = drawableId),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .onGloballyPositioned { coordinates ->
                height = coordinates.size.height
            }
    )

    return height.dp
}