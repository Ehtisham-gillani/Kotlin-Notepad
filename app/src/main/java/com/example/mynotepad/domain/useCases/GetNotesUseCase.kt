package com.example.mynotepad.domain.useCases

import com.example.mynotepad.domain.NoteRepository
import com.example.mynotepad.domain.NoteResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetNotesUseCase(private val repository: NoteRepository) {
    operator fun invoke(): Flow<NoteResult> = flow {
        try {
            repository.getAllNotes().collect { notes ->
                emit(NoteResult.Success(notes))
            }
        } catch (e: Exception) {
            emit(NoteResult.Error(e))
        }
    }
}