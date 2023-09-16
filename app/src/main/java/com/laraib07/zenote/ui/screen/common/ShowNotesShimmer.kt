package com.laraib07.zenote.ui.screen.common

import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShowNotesShimmer(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    val shimmerColors = listOf(
        MaterialTheme.colorScheme.surfaceVariant.copy(0.8f),
        MaterialTheme.colorScheme.surfaceVariant.copy(0.2f),
        MaterialTheme.colorScheme.surfaceVariant.copy(0.8f)
    )
    val transition = rememberInfiniteTransition()
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    val heights by remember {
        mutableStateOf(List(10) { (128..256).random().dp })
    }

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )

    LazyVerticalStaggeredGrid(
        modifier = modifier
            .padding(paddingValues)
            .fillMaxSize(),
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 8.dp
        ),
        verticalItemSpacing = 4.dp,
        columns = StaggeredGridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        userScrollEnabled = false
    ) {
        items(heights) { height ->
            ShimmerItem(brush = brush, height = height)
        }
    }
}

@Composable
fun ShimmerItem(
    brush: Brush,
    height: Dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(CardDefaults.shape)
            .background(
                brush = brush
            )
    )
}

@Preview(showBackground = true)
@Composable
fun ShimmerEffectPreview() {
    ShowNotesShimmer(paddingValues = PaddingValues())
}