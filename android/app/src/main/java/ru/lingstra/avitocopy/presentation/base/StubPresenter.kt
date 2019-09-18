package ru.lingstra.avitocopy.presentation.base

import ru.lingstra.avitocopy.domain.common.SearchFilter
import ru.lingstra.avitocopy.toothpick.FilterSearch
import javax.inject.Inject


class StubPresenter @Inject constructor(@FilterSearch val filter: SearchFilter) : BaseMviPresenter<StubView, Any>() {
    override fun bindIntents() {}
}