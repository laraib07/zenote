package com.laraib07.zenote.ui.screen.deleted

import androidx.lifecycle.ViewModel
import com.laraib07.zenote.data.model.Folder
import com.laraib07.zenote.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class DeletedViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {
    val notes = repository.getAllNotes.map { noteList ->
        noteList.filter { note -> note.baseNote.folder == Folder.DELETED }
    }
}