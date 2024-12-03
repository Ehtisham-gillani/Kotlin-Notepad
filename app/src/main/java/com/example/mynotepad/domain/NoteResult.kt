package com.example.mynotepad.domain

import com.example.mynotepad.data.Note

sealed class NoteResult {
    data class Success(val data: List<Note>) : NoteResult()
    data class Error(val exception: Exception) : NoteResult()
}