package ru.lingstra.avitocopy.domain.list

import ru.lingstra.avitocopy.domain.common.CarListElement

sealed class CarsListPartialState(private val logMessage: String) {
    object StartLoad: CarsListPartialState("StartLoad")
    object EndLoad: CarsListPartialState("EndLoad")
    object NotFoundMore: CarsListPartialState("NotFoundMore")
    data class Cars(val cars: List<CarListElement>): CarsListPartialState("Cars $cars")
    data class Error(val error: Throwable): CarsListPartialState("Error $error")
    data class Original(val url: String): CarsListPartialState("Original $url")

    override fun toString(): String = logMessage
    fun partial() = this
}