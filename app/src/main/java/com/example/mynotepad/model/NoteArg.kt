package com.example.mynotepad.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NoteArg(
    val noteId: Long,
    val title: String,
    val content: String
) : Parcelable