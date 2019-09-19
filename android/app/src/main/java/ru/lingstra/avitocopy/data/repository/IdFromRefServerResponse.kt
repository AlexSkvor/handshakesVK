package ru.lingstra.avitocopy.data.repository

data class IdFromRefServerResponse(
    val response: List<Response>
)

data class Response(
    val id: Int,
    val is_closed: Boolean
)