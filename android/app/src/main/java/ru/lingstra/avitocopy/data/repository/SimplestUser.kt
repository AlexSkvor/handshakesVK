package ru.lingstra.avitocopy.data.repository

data class SimplestUser(
    val id: Int,
    val checked: Boolean = false,
    val parent: SimplestUser? = null,
    val level: Int = 0
)