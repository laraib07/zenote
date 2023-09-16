package com.laraib07.zenote.ui.screen.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.laraib07.zenote.R

@Composable
fun ZenoteHeader(
    onTap: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(id = R.drawable.ic_zenote_header),
        modifier = modifier
            .fillMaxWidth(0.5f)
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .clickable { onTap() },
        contentDescription = null,
        colorFilter = ColorFilter.tint(
            color = MaterialTheme.colorScheme.surfaceTint
        )
    )
}

@Preview(showBackground = true)
@Composable
fun ZenoteHeaderPrev() {
    ZenoteHeader(onTap = { })
}

@Preview(
    showBackground = true,
    wallpaper = Wallpapers.RED_DOMINATED_EXAMPLE
)
@Composable
fun ZenoteHeaderPrev2() {
    ZenoteHeader(onTap = { })
}

@Preview(
    showBackground = true,
    wallpaper = Wallpapers.YELLOW_DOMINATED_EXAMPLE
)
@Composable
fun ZenoteHeaderPrev3() {
    ZenoteHeader(onTap = { })
}
