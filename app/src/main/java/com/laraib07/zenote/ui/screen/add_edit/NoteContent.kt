package com.laraib07.zenote.ui.screen.add_edit

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.laraib07.zenote.ui.screen.common.NoteEditorTextField
import com.laraib07.zenote.ui.theme.noteContentTextStyle
import kotlinx.coroutines.launch

@Composable
internal fun NoteContent(
    viewMode: NoteViewMode,
    titleTFV: TextFieldValue,
    contentTFV: TextFieldValue,
    onTitleChange: (TextFieldValue) -> Unit,
    onContentChange: (TextFieldValue) -> Unit
) {
    when (viewMode) {
        NoteViewMode.EDIT -> {
            NoteEditor(
                titleTFV = titleTFV,
                contentTFV = contentTFV,
                onTitleChange = onTitleChange,
                onContentChange = onContentChange,
            )
        }

        NoteViewMode.PREVIEW -> {
            NotePreview(
                titleTFV = titleTFV,
                contentTFV = contentTFV
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun NoteEditor(
    titleTFV: TextFieldValue,
    contentTFV: TextFieldValue,
    onTitleChange: (TextFieldValue) -> Unit,
    onContentChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val bringIntoViewRequester = BringIntoViewRequester()
    val (titleFocusRequester, contentFocusRequester) = remember { FocusRequester.createRefs() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        if (titleTFV.text.isEmpty()) {
            titleFocusRequester.requestFocus()
        } else {
            contentFocusRequester.requestFocus()
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        NoteEditorTextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 8.dp)
                .focusRequester(titleFocusRequester),
            value = titleTFV,
            onChangedVal = onTitleChange,
            placeholder = "Title",
            textStyle = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.onBackground
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.moveFocus(FocusDirection.Down) }
            )
        )
        NoteEditorTextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 8.dp)
                .focusRequester(contentFocusRequester)
                .onFocusEvent { event ->
                    if (event.isFocused) {
                        scope.launch {
                            bringIntoViewRequester.bringIntoView()
                        }
                    }
                },
            value = contentTFV,
            onChangedVal = onContentChange,
            placeholder = "Take a note...",
            textStyle = MaterialTheme.typography.noteContentTextStyle.copy(
                color = MaterialTheme.colorScheme.onBackground.copy(
                    alpha = 0.8f
                )
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
            )
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .bringIntoViewRequester(bringIntoViewRequester)
        )
    }
}

@Composable
fun NotePreview(
    titleTFV: TextFieldValue,
    contentTFV: TextFieldValue,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 8.dp),
            text = titleTFV.text,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 8.dp),
            text = contentTFV.text.trim(),
            style = MaterialTheme.typography.noteContentTextStyle,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
        )
    }
}