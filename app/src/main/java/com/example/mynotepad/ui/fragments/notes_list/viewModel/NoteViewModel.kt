package com.example.mynotepad.ui.fragments.notes_list.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotepad.data.Note
import com.example.mynotepad.domain.NoteRepository
import com.example.mynotepad.domain.NoteResult
import com.example.mynotepad.domain.useCases.GetNotesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteViewModel(
    private val getNotesUseCase: GetNotesUseCase,
    private val repository: NoteRepository
) : ViewModel() {
    private val _notesState = MutableStateFlow<NoteResult>(NoteResult.Success(emptyList()))
    val notesState: StateFlow<NoteResult> = _notesState.asStateFlow()

    init {
        viewModelScope.launch {
            getNotesUseCase().collect { result ->
                _notesState.value = result
            }
        }
    }

    fun addNote(title: String, content: String) = viewModelScope.launch {
        try {
            repository.addNote(Note(title = title, content = content))
        } catch (e: Exception) {
            _notesState.value = NoteResult.Error(e)
        }
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        try {
            repository.updateNote(note)
        } catch (e: Exception) {
            _notesState.value = NoteResult.Error(e)
        }
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        try {
            repository.deleteNote(note)
        } catch (e: Exception) {
            _notesState.value = NoteResult.Error(e)
        }
    }
}
