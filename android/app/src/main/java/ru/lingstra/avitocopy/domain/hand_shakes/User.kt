package ru.lingstra.avitocopy.domain.hand_shakes

data class User(
    val name: String,
    val surname: String,
    val photo: String,
    val id: String
) {
    val link = "https://vk.com/$id"
}