package com.laraib07.zenote.ui.screen.notes

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.laraib07.zenote.ui.components.UnderDevelopment

@Composable
fun SortMenuDialog(
    noteOrder: NoteOrder,
    onDismiss: () -> Unit,
    onConfirm: (NoteOrder) -> Unit
) {
    var order by remember { mutableStateOf(noteOrder) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Sort Notes")
        },
        text = {
            UnderDevelopment()
        },
        confirmButton = {
            Button(onClick = { onConfirm(order) }) {
                Text(text = "Done")
            }
        }
    )
}

@Composable
private fun OrderBy(orderBy: NoteOrder) {

}

@Composable
private fun OrderIn(orderIn: OrderType) {

}

@Preview
@Composable
fun SortMenuDialogPrev() {
    SortMenuDialog(
        noteOrder = NoteOrder.MODIFIED(OrderType.DESCENDING),
        onDismiss = { /*TODO*/ },
        onConfirm = {}
    )
}