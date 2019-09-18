package ru.lingstra.avitocopy.presentation.list

import android.content.Context
import android.content.Intent
import android.net.Uri
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import ru.lingstra.avitocopy.R
import ru.lingstra.avitocopy.domain.list.CarsListInteractor
import ru.lingstra.avitocopy.domain.list.CarsListPartialState
import ru.lingstra.avitocopy.domain.list.CarsListViewState
import ru.lingstra.avitocopy.presentation.base.BaseMviPresenter
import ru.lingstra.avitocopy.system.ResourceManager
import ru.lingstra.avitocopy.system.SystemMessage
import javax.inject.Inject

class CarsListPresenter @Inject constructor(
    private val resourceManager: ResourceManager,
    private val systemMessage: SystemMessage,
    private val interactor: CarsListInteractor,
    private val context: Context
) : BaseMviPresenter<CarsListView, CarsListViewState>() {

    override fun bindIntents() {
        val initialState = CarsListViewState()
        val actions = getActions().share()
        subscribeActions(actions)
        subscribeViewState(
            actions.scan(initialState, reducer).distinctUntilChanged(),
            CarsListView::render
        )
    }

    private val reducer = BiFunction { oldState: CarsListViewState, partial: CarsListPartialState ->
        when (partial) {
            is CarsListPartialState.Cars -> oldState.copy(cars = partial.cars, render = CarsListViewState.Render.CARS)
            else -> oldState.copy(render = CarsListViewState.Render.NONE)
        }
    }

    private fun subscribeActions(actions: Observable<CarsListPartialState>) {
        actions.subscribe {
            when (it) {
                is CarsListPartialState.EndLoad -> systemMessage.showProgress(false)
                is CarsListPartialState.StartLoad -> systemMessage.showProgress(true)
                is CarsListPartialState.Error -> {
                    systemMessage.showProgress(false)
                    systemMessage.send(resourceManager.getString(R.string.errorHappened))
                }
                is CarsListPartialState.Original -> context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it.url)))
            }
        }.bind()
    }

    private fun getActions(): Observable<CarsListPartialState> {
        val load = intent(CarsListView::loadMore)
            .switchMap { interactor.load(it) }.share()

        val firstLoad = intent(CarsListView::initialLoad)
            .switchMap { interactor.load(0) }

        val original = intent(CarsListView::goToOriginal)
            .map { CarsListPartialState.Original(it).partial() }

        val empty = load.filter { it is CarsListPartialState.Cars }
            .map { (it as CarsListPartialState.Cars).cars }
            .filter { it.isEmpty() }
            .map { CarsListPartialState.NotFoundMore }

        return Observable.merge(load, original, empty, firstLoad)
    }
}