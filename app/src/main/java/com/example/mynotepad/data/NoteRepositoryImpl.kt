package com.example.mynotepad.data

import com.example.mynotepad.domain.NoteRepository
import com.example.mynotepad.model.Note
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val noteDao: NoteDao
) : NoteRepository {
    override fun getAllNotes(): Flow<List<Note>> = noteDao.getAllNotes()
    override suspend fun getNoteById(id: Long): Note? = noteDao.getNoteById(id)
    override suspend fun addNote(note: Note): Long = noteDao.insertNote(note)
    override suspend fun updateNote(note: Note) = noteDao.updateNote(note)
    override suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)
    override fun getFavoriteNotes(): Flow<List<Note>> = noteDao.getFavoriteNotes()
}