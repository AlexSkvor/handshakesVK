package ru.lingstra.avitocopy.domain.hand_shakes

data class SimpleServerAnswer(
    val response: Response
)

data class Response(
    val count: Int,
    val items: List<Int>
)