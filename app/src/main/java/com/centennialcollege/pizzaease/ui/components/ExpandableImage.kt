package com.centennialcollege.pizzaease.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

//animation that shows a bigger size of the image when tapped
@Composable
fun ExpandableImage(
    drawableId: Int,
    modifier: Modifier = Modifier,
) {
    val expandedState = remember { mutableStateOf(false) }

    val height = animateDpAsState(
        targetValue = if (expandedState.value) 1000.dp else 200.dp, // Adjust the expanded height
        animationSpec = tween(durationMillis = 1000)
    )

    Box(modifier = modifier.height(height.value)) {
        Image(
            painter = painterResource(id = drawableId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expandedState.value = !expandedState.value
                },
        )
    }
}