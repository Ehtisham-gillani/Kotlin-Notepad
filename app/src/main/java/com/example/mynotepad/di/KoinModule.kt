package com.example.mynotepad.di

import androidx.room.Room
import com.example.mynotepad.data.NoteDatabase
import com.example.mynotepad.data.NoteRepositoryImpl
import com.example.mynotepad.domain.NoteRepository
import com.example.mynotepad.domain.useCases.GetNotesUseCase
import com.example.mynotepad.ui.fragments.notes_list.viewModel.NoteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    // Database setup
    single {
        Room.databaseBuilder(
            get(),
            NoteDatabase::class.java,
            "notes.db"
        ).build()
    }

    // Provide DAO
    single { get<NoteDatabase>().noteDao() }

    // Repository implementation
    single<NoteRepository> { NoteRepositoryImpl(get()) }

    // Use Cases
    single { GetNotesUseCase(get()) }

    // ViewModels
    viewModel() { NoteViewModel(get(), get()) }
}