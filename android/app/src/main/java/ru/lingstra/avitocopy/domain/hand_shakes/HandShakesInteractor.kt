package ru.lingstra.avitocopy.domain.hand_shakes

import io.reactivex.Observable
import ru.lingstra.avitocopy.data.repository.HandShakesRepository
import ru.lingstra.avitocopy.endWith
import javax.inject.Inject

class HandShakesInteractor @Inject constructor(
    private val repository: HandShakesRepository
) {

    fun load(input: Pair<String, String>): Observable<HandShakesPartialState> =
        repository.searchHandShakes(input.first, input.second)
            .map { HandShakesPartialState.Answer(it).partial() }
            .startWith(HandShakesPartialState.Loading(true))
            .onErrorReturn { HandShakesPartialState.Error(it) }
            .endWith(HandShakesPartialState.Loading(false))

    fun queries(): Observable<HandShakesPartialState> = repository.queries()
        .map { HandShakesPartialState.Query.partial() }
}