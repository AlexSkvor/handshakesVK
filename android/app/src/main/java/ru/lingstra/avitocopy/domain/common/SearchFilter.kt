package ru.lingstra.avitocopy.domain.common

data class SearchFilter(
    var minPrice: Int = 0,
    var maxPrice: Int = 99999999,
    var minYear: Int = 0,
    var take: Int = 20,
    var maxYear: Int = 9999,
    var cities: List<String> = listOf(),
    var colors: List<String> = listOf(),
    var bodyTypes: List<String> = listOf(),
    var sort: String = "Дешевые",
    var sources: List<String> = listOf(),
    var filterResellers: Boolean = false,
    var marksWithModels: List<MarkWithModels> = listOf()
)