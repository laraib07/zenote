package com.laraib07.zenote.ui.screen.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NoteEditorTextField(
    value: TextFieldValue,
    onChangedVal: (TextFieldValue) -> Unit,
    placeholder: String,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    BasicTextField(
        value = value,
        onValueChange = onChangedVal,
        textStyle = textStyle,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        decorationBox = { innerTextField ->
            Box {
                if (value.text.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = textStyle,
                        color = MaterialTheme.colorScheme.onBackground.copy(0.6f)
                    )
                }
                innerTextField()
            }
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun NoteEditorPrev() {
    NoteEditorTextField(
        value = TextFieldValue(),
        onChangedVal = {},
        placeholder = "Title",
        textStyle = MaterialTheme.typography.titleMedium
    )
}

@Preview(showBackground = true)
@Composable
fun NoteEditorPrev2() {
    NoteEditorTextField(
        value = TextFieldValue("Hello there..."),
        onChangedVal = {},
        placeholder = "Title",
        textStyle = MaterialTheme.typography.titleMedium
    )
}

@Preview(showBackground = true)
@Composable
fun NoteEditorPrev3() {
    NoteEditorTextField(
        value = TextFieldValue("Hello there..."),
        onChangedVal = {},
        placeholder = "Title",
        textStyle = MaterialTheme.typography.titleMedium
    )
}