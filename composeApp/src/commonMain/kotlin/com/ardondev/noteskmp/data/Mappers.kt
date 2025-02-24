package com.ardondev.noteskmp.data

import com.ardondev.noteskmp.data.local.entity.NoteEntity
import com.ardondev.noteskmp.domain.model.Note

fun NoteEntity.toNote(): Note {
    return Note(id, title, text, color, clipped, modificationDate)
}

fun Note.toEntity(): NoteEntity {
    return NoteEntity(id, title, text, clipped, color, modificationDate)
}