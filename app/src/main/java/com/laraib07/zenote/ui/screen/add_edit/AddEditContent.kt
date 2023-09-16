package com.laraib07.zenote.ui.screen.add_edit

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.laraib07.zenote.data.model.Todo
import com.laraib07.zenote.ui.components.DateItem
import com.laraib07.zenote.ui.components.TagItem

private const val TAG = "AddEditContent"

@OptIn(ExperimentalLayoutApi::class, ExperimentalAnimationApi::class)
@Composable
fun AddEditContent(
    scrollState: ScrollState,
    paddingValues: PaddingValues,
    viewMode: NoteViewMode,
    noteContentMode: NoteContentMode,
    isPreview: Boolean,
    titleTFV: TextFieldValue,
    contentTFV: TextFieldValue,
    onTitleChange: (TextFieldValue) -> Unit,
    onContentChange: (TextFieldValue) -> Unit,
    todos: List<Todo>,
    tags: List<String>
) {
    Column(
        modifier = Modifier
            .animateContentSize()
            .verticalScroll(scrollState)
            .padding(paddingValues)
            .padding(horizontal = 16.dp)
    ) {
        AnimatedContent(
            targetState = noteContentMode,
            transitionSpec = {
                ContentTransform(
                    targetContentEnter = fadeIn(),
                    initialContentExit = fadeOut()
                )
            }
        ) { noteContentMode ->
            when (noteContentMode) {
                NoteContentMode.NOTE -> {
                    NoteContent(
                        viewMode = viewMode,
                        titleTFV = titleTFV,
                        contentTFV = contentTFV,
                        onTitleChange = onTitleChange,
                        onContentChange = onContentChange
                    )
                }

                NoteContentMode.CHECKLIST -> {
                    ChecklistContent(
                        viewMode = viewMode,
                        titleTVF = titleTFV,
                        onTitleChange = onTitleChange,
                        todos = todos
                    )
                }
            }
        }
        Spacer(
            modifier = Modifier
                .animateContentSize()
                .height(if (isPreview) 16.dp else 32.dp)
        )
        FlowRow {
            tags.forEach {
                TagItem(
                    title = it,
                    modifier = Modifier.padding(end = 2.dp, bottom = 4.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        AnimatedVisibility(
            visible = isPreview,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column(modifier = Modifier) {
                DateItem(
                    date = 0L,
                    prompt = "Created at",
                    textStyle = MaterialTheme.typography.bodySmall.copy(
                        fontStyle = FontStyle.Italic
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                )
                DateItem(
                    date = 0L,
                    prompt = "Last modified at",
                    textStyle = MaterialTheme.typography.bodySmall.copy(
                        fontStyle = FontStyle.Italic
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}