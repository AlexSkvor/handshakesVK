package ru.lingstra.avitocopy.data

import io.reactivex.Observable
import ru.lingstra.avitocopy.domain.common.CarListElement
import ru.lingstra.avitocopy.domain.common.SearchFilter
import ru.lingstra.avitocopy.system.network.NetworkApi
import ru.lingstra.avitocopy.system.schedulers.SchedulersProvider
import ru.lingstra.avitocopy.toothpick.FilterSearch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CarsListSearchRepository @Inject constructor(
    private val api: NetworkApi,
    private val scheduler: SchedulersProvider,
    @FilterSearch private val filter: SearchFilter
) {
    fun delay() = Observable.just(listOf<CarListElement>())
        .delay(100L, TimeUnit.MILLISECONDS)
        .subscribeOn(scheduler.io())
        .observeOn(scheduler.ui())

    fun load(skip: Int): Observable<List<CarListElement>> =
        api.getCars(
            skip = skip,
            take = filter.take,
            colors = filter.colors,
            bodyTypes = filter.bodyTypes,
            cities = filter.cities,
            sort = filter.sort,
            sources = filter.sources,
            minPrice = filter.minPrice,
            maxPrice = filter.maxPrice,
            minYear = filter.minYear,
            maxYear = filter.maxYear,
            filterResellers = filter.filterResellers
        )
            .map { it.data }
            .map { it.map { car -> car.toCarListElement() } }
            .toObservable()
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
}