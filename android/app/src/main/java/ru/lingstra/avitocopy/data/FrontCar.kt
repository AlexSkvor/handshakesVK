package ru.lingstra.avitocopy.data

import ru.lingstra.avitocopy.domain.common.CarListElement

data class FrontCar(
    var id: Int = 0,
    val originalUrl: String = "",
    val imageUrl: String = "",
    val tradeMark: String = "",
    val model: String = "",
    val color: String = "",
    val driveUnit: String = "",
    val price: Int = -1,
    val medianPrice: Int = -1,
    val year: Int = -1,
    val bodyType: String = "",
    val steeringSide: String = "",
    val mileagePerYear: Int = -1,
    val comment: String,
    val actualizationTime: String,
    val source: String,
    val totalMileage: Int = -1,
    val city: String
) {

    fun toCarListElement(): CarListElement =
        CarListElement(
            price = price,
            year = year,
            comment = comment,
            originalUrl = originalUrl,
            bodyType = bodyType,
            city = city,
            driveUnit = driveUnit,
            imageUrl = if (imageUrl.startsWith("http")) imageUrl else "http:$imageUrl",
            medianPrice = medianPrice,
            mark = tradeMark,
            model = model,
            source = source,
            steeringSide = steeringSide,
            totalMileage = totalMileage,
            mileagePerYear = mileagePerYear,
            color = color
        )

}