package ru.lingstra.avitocopy.ui.list

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_cars_list.*
import ru.lingstra.avitocopy.R
import ru.lingstra.avitocopy.domain.list.CarsListViewState
import ru.lingstra.avitocopy.presentation.list.CarsListPresenter
import ru.lingstra.avitocopy.presentation.list.CarsListRecyclerAdapter
import ru.lingstra.avitocopy.presentation.list.CarsListView
import ru.lingstra.avitocopy.ui.base.MviBaseFragment
import ru.lingstra.avitocopy.visible

class CarsListFragment : MviBaseFragment<CarsListView, CarsListPresenter>(), CarsListView {
    override val layoutRes: Int
        get() = R.layout.fragment_cars_list

    private lateinit var adapter: CarsListRecyclerAdapter

    private val loadingIntent: PublishRelay<Unit> = PublishRelay.create()
    override fun initialLoad(): Observable<Unit> = loadingIntent.hide()

    override fun createPresenter(): CarsListPresenter {
        return scope.getInstance(CarsListPresenter::class.java)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        adapter = CarsListRecyclerAdapter(arrayListOf())
    }

    override fun loadMore(): Observable<Int> =
        buttonMore.clicks()
            .map { adapter.itemCount }

    override fun goToOriginal(): Observable<String> =
        adapter.getGoToOriginalEvent()
            .map { it.originalUrl }

    override fun render(state: CarsListViewState) {
        addCars(state)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
    }

    override fun onResume() {
        super.onResume()
        loadingIntent.accept(Unit)
    }

    private fun setupRecycler() {
        carsListRecycler.layoutManager = LinearLayoutManager(requireContext())
        carsListRecycler.adapter = adapter
    }

    private fun addCars(state: CarsListViewState) {
        if (state.render == CarsListViewState.Render.CARS) {
            changeVisibilityMode(state.cars.isNotEmpty())
            adapter.addData(state.cars)
        }
    }

    private fun changeVisibilityMode(showRecycler: Boolean) {
        carsListRecycler.visible = showRecycler
        notFoundCars.visible = !showRecycler
    }
}