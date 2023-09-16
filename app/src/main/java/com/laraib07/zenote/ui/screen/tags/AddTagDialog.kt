package com.laraib07.zenote.ui.screen.tags

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.laraib07.zenote.R

@Composable
fun AddTagDialog(
    tagTFV: TextFieldValue,
    onTagTFVChange: (TextFieldValue) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val focusRequester = FocusRequester()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(id = R.string.add_tag_dialog),
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            OutlinedTextField(
                value = tagTFV,
                singleLine = true,
                onValueChange = onTagTFVChange,
                modifier = Modifier.focusRequester(focusRequester),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.add_tag_placeholder),
                        color = MaterialTheme.colorScheme.onBackground.copy(0.6f)
                    )
                },
                keyboardActions = KeyboardActions(onDone = { onConfirm() }),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
            )

            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm
            ) {
                Text(stringResource(id = R.string.add_tag_button))
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss
            ) {
                Text(stringResource(id = R.string.dismiss_tag_button))
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AddTagDialogPreview() {
    AddTagDialog(
        tagTFV = TextFieldValue(),
        onTagTFVChange = {},
        onConfirm = {},
        onDismiss = {}
    )
}