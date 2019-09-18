package ru.lingstra.avitocopy.presentation.list

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable
import ru.lingstra.avitocopy.domain.hand_shakes.HandShakesViewState

interface HandShakesView: MvpView {
    fun initialLoad(): Observable<Unit> = Observable.just(Unit)

    fun render(state: HandShakesViewState)
}