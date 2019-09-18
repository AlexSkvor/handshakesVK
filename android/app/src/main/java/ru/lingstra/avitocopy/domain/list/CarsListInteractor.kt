package ru.lingstra.avitocopy.domain.list

import io.reactivex.Observable
import ru.lingstra.avitocopy.data.CarsListSearchRepository
import ru.lingstra.avitocopy.endWith
import javax.inject.Inject

class CarsListInteractor @Inject constructor(
    private val repository: CarsListSearchRepository
) {

    fun load(skip: Int): Observable<CarsListPartialState> =
        repository.delay()
            .map { CarsListPartialState.Cars(it).partial() }
            .concatWith(
                repository.load(skip)
                    .map { CarsListPartialState.Cars(it).partial() }
                    .startWith(CarsListPartialState.StartLoad)
                    .endWith(CarsListPartialState.EndLoad)
                    .onErrorReturn { CarsListPartialState.Error(it) })
}