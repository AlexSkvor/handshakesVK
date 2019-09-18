package ru.lingstra.avitocopy.data

import ru.lingstra.avitocopy.domain.common.CarListElement

data class SearchCarsResultList(
    val status: String,
    val code: Int,
    val data: List<FrontCar>
) {
}