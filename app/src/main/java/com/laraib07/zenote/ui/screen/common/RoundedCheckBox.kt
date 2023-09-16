package com.laraib07.zenote.ui.screen.common

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.laraib07.zenote.ui.theme.ZenoteTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RoundedCheckBox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    size: Dp = 20.dp,
    borderWidth: Dp = 2.dp
) {
    val boxSize by animateDpAsState(
        targetValue = if (checked) size + 4.dp else size,
    )
    val bgColor by animateColorAsState(
        targetValue = if (checked) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.primary.copy(alpha = 0f)
        }
    )
    val borderColor by animateColorAsState(
        targetValue = if (checked) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)

        },
    )

    val toggleableModifier = if (enabled) {
        Modifier
            .toggleable(
                value = checked,
                onValueChange = onCheckedChange,
                role = Role.Checkbox
            )
    } else {
        Modifier
    }
    Box(
        modifier = modifier.size(size + 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(boxSize)
                .clip(RoundedCornerShape(4.dp))
                .border(borderWidth, borderColor, RoundedCornerShape(4.dp))
                .background(bgColor)
                .animateContentSize()
                .padding(2.dp)
                .then(toggleableModifier),
            contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(
                visible = checked,
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut()
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

        }
    }
}

@Preview(showBackground = true, wallpaper = Wallpapers.RED_DOMINATED_EXAMPLE)
@Composable
fun RoundedCheckBoxPrev() {
    var checked by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        RoundedCheckBox(checked = checked, onCheckedChange = { checked = !checked })
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun RoundedCheckBoxPrev2() {
    var checked by remember { mutableStateOf(false) }
    var checked2 by remember { mutableStateOf(false) }
    ZenoteTheme {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RoundedCheckBox(
                    modifier = Modifier.padding(16.dp),
                    checked = checked,
                    onCheckedChange = { checked = !checked }
                )
                Text(text = if (checked) "checked" else "unchecked")
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RoundedCheckBox(
                    modifier = Modifier.padding(16.dp),
                    checked = checked2,
                    onCheckedChange = { checked2 = !checked2 }
                )
                Text(text = if (checked2) "checked" else "unchecked")
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RoundedCheckBox(
                    modifier = Modifier.padding(16.dp),
                    checked = checked2,
                    onCheckedChange = { checked2 = !checked2 },
                    size = 16.dp
                )
                Text(text = if (checked2) "checked" else "unchecked")
            }
        }
    }
}