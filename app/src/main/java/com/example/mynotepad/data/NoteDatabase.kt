package com.example.mynotepad.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mynotepad.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}