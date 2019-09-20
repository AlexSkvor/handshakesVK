package ru.lingstra.avitocopy.data.repository

data class IdFromRefServerResponse(
    val response: List<Response>
)

data class Response(
    val id: Int,
    val is_closed: Boolean,
    val first_name: String,
    val last_name: String,
    val photo_50: String
)