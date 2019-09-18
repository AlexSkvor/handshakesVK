package ru.lingstra.avitocopy.presentation.token

import ru.lingstra.avitocopy.alsoPrintDebug
import ru.lingstra.avitocopy.presentation.base.BaseMviPresenter
import javax.inject.Inject

class TokenPresenter @Inject constructor(

) : BaseMviPresenter<TokenView, Any>() {
    override fun bindIntents() {
        intent(TokenView::newToken)
            .subscribe {
                it.alsoPrintDebug("AAAAAAAAAA")
            }.bind()
    }
}