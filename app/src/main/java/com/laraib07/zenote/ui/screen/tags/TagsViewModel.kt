package com.laraib07.zenote.ui.screen.tags

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laraib07.zenote.data.model.Tag
import com.laraib07.zenote.data.repository.NoteRepository
import com.laraib07.zenote.data.repository.TagRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagsViewModel @Inject constructor(
    private val repository: TagRepository
) : ViewModel() {
    val tagsList = repository.getAllTags()

    var tagTFV by mutableStateOf(TextFieldValue())
        private set

    fun onTagTFVChange(tag: TextFieldValue) {
        tagTFV = tag
    }

    fun addTags() {
        val newTags = tagTFV.text.split(",")
            .filter { it.isNotEmpty() && it.isNotBlank() }
            .map { Tag(tagContent = it.trim()) }

        if (newTags.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                if (!repository.tagAlreadyExists(newTags.first().tagContent)) {
                    repository.addAllTag(newTags)
                }
            }
        }
        onTagTFVChange(TextFieldValue())

    }
}