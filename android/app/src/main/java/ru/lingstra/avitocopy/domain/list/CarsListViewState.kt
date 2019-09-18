package ru.lingstra.avitocopy.domain.list

import ru.lingstra.avitocopy.domain.common.CarListElement

data class CarsListViewState(
    val cars: List<CarListElement> = listOf(),
    val render: Render = Render.NONE
) {
    enum class Render {
        NONE, CARS
    }
}