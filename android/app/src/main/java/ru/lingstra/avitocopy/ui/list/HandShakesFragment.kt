package ru.lingstra.avitocopy.ui.list

import ru.lingstra.avitocopy.R
import ru.lingstra.avitocopy.domain.hand_shakes.HandShakesViewState
import ru.lingstra.avitocopy.presentation.list.HandShakesPresenter
import ru.lingstra.avitocopy.presentation.list.HandShakesView
import ru.lingstra.avitocopy.ui.base.MviBaseFragment

class HandShakesFragment : MviBaseFragment<HandShakesView, HandShakesPresenter>(), HandShakesView {
    override val layoutRes: Int
        get() = R.layout.fragment_hand_shakes

    override fun createPresenter(): HandShakesPresenter =
        scope.getInstance(HandShakesPresenter::class.java)


    override fun render(state: HandShakesViewState) {

    }
}