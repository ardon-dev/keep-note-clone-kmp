package com.ardondev.noteskmp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val title: String,
    val text: String,
    val clipped: Boolean = false,
    val color: String,
    val modificationDate: String
)
