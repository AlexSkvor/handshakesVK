package ru.lingstra.avitocopy.domain.common

data class CarListElement(
    val price: Int = 0,
    val year: Int = 0,
    val comment: String = "",
    val originalUrl: String = "",
    val bodyType: String = "",
    val city: String = "",
    val driveUnit: String = "",
    val imageUrl: String = "",
    val medianPrice: Int = 0,
    val mark: String = "",
    val model: String = "",
    val source: String = "",
    val steeringSide: String = "",
    val totalMileage: Int = 0,
    val mileagePerYear: Int = 0,
    val color: String = ""
)