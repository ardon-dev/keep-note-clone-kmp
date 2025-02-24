package com.ardondev.noteskmp.domain.model

data class Note(
    val id: Long? = null,
    val title: String = "",
    val text: String = "",
    val color: String = "",
    val clipped: Boolean = false,
    val modificationDate: String = ""
)