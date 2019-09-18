package ru.lingstra.avitocopy.presentation.list

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import ru.lingstra.avitocopy.R
import ru.lingstra.avitocopy.domain.hand_shakes.HandShakesInteractor
import ru.lingstra.avitocopy.domain.hand_shakes.HandShakesPartialState
import ru.lingstra.avitocopy.domain.hand_shakes.HandShakesViewState
import ru.lingstra.avitocopy.presentation.base.BaseMviPresenter
import ru.lingstra.avitocopy.system.ResourceManager
import ru.lingstra.avitocopy.system.SystemMessage
import timber.log.Timber
import javax.inject.Inject

class HandShakesPresenter @Inject constructor(
    private val resourceManager: ResourceManager,
    private val systemMessage: SystemMessage,
    private val interactor: HandShakesInteractor
) : BaseMviPresenter<HandShakesView, HandShakesViewState>() {

    override fun bindIntents() {
        val initialState = HandShakesViewState()
        val actions = getActions().share()
        subscribeActions(actions)
        subscribeViewState(
            actions.scan(initialState, reducer).distinctUntilChanged(),
            HandShakesView::render
        )
    }

    private val reducer = BiFunction { oldState: HandShakesViewState, it: HandShakesPartialState ->
        when (it) {
            is HandShakesPartialState.Answer -> oldState.copy(answerList = it.list)
            else -> oldState
        }
    }

    private fun subscribeActions(actions: Observable<HandShakesPartialState>) {
        actions.subscribe {
            when (it) {
                is HandShakesPartialState.Loading -> systemMessage.showProgress(it.show)
                is HandShakesPartialState.Error -> {
                    Timber.e(it.error)
                    systemMessage.showProgress(false)
                    systemMessage.send(resourceManager.getString(R.string.errorHappened))
                }
            }
        }.bind()
    }

    private fun getActions(): Observable<HandShakesPartialState> {
        val firstLoad = intent(HandShakesView::initialLoad)
            .switchMap { interactor.load() }

        val actionsList = listOf(firstLoad)

        return Observable.merge(actionsList)
    }
}