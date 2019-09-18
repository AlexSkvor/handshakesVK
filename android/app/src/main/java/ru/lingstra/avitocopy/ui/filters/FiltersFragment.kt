package ru.lingstra.avitocopy.ui.filters

import android.os.Bundle
import android.view.View
import com.jakewharton.rxbinding2.widget.textChanges
import kotlinx.android.synthetic.main.fragment_filters.*
import ru.lingstra.avitocopy.R
import ru.lingstra.avitocopy.presentation.base.StubPresenter
import ru.lingstra.avitocopy.presentation.base.StubView
import ru.lingstra.avitocopy.toIntOrMinusOne
import ru.lingstra.avitocopy.ui.base.MviBaseFragment

class FiltersFragment : MviBaseFragment<StubView, StubPresenter>() {
    override fun createPresenter(): StubPresenter {
        presenter = scope.getInstance(StubPresenter::class.java)
        return presenter
    }

    private lateinit var presenter: StubPresenter

    override val layoutRes: Int
        get() = R.layout.fragment_filters

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        minYearEdit.setText(presenter.filter.minYear.toString())
        maxYearEdit.setText(presenter.filter.maxYear.toString())
        minPriceEdit.setText(presenter.filter.minPrice.toString())
        maxPriceEdit.setText(presenter.filter.maxPrice.toString())
    }

    override fun onResume() {
        super.onResume()
        minYearEdit.textChanges()
            .skipInitialValue()
            .map { it.toString().toIntOrMinusOne() }
            .filter { it >= 0 }
            .subscribe {
                presenter.filter.minYear = it
            }.bind()

        maxYearEdit.textChanges()
            .skipInitialValue()
            .map { it.toString().toIntOrMinusOne() }
            .filter { it >= 0 }
            .subscribe {
                presenter.filter.maxYear = it
            }.bind()

        minPriceEdit.textChanges()
            .skipInitialValue()
            .map { it.toString().toIntOrMinusOne() }
            .filter { it >= 0 }
            .subscribe {
                presenter.filter.minPrice = it
            }.bind()

        maxPriceEdit.textChanges()
            .skipInitialValue()
            .map { it.toString().toIntOrMinusOne() }
            .filter { it >= 0 }
            .subscribe {
                presenter.filter.maxPrice = it
            }.bind()
    }
}