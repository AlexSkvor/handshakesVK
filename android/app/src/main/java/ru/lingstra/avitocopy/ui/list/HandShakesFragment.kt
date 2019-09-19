package ru.lingstra.avitocopy.ui.list

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_hand_shakes.*
import ru.lingstra.avitocopy.R
import ru.lingstra.avitocopy.domain.hand_shakes.HandShakesViewState
import ru.lingstra.avitocopy.presentation.list.HandShakesPresenter
import ru.lingstra.avitocopy.presentation.list.HandShakesView
import ru.lingstra.avitocopy.ui.base.MviBaseFragment
import ru.lingstra.avitocopy.ui.utils.delegate.CompositeDelegateAdapter

class HandShakesFragment : MviBaseFragment<HandShakesView, HandShakesPresenter>(), HandShakesView {

    override val layoutRes: Int
        get() = R.layout.fragment_hand_shakes

    override fun createPresenter(): HandShakesPresenter =
        scope.getInstance(HandShakesPresenter::class.java)

    private lateinit var adapter: CompositeDelegateAdapter<Int>

    override fun render(state: HandShakesViewState) {
        adapter.replaceData(state.answerList)
    }

    override fun startSearch(): Observable<Pair<String, String>> =
        start.clicks()
            .map { firstRefEdit.text.toString() to secondRefEdit.text.toString() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        adapter = CompositeDelegateAdapter.Companion.Builder<Int>()
            .add(IntAnswerAdapter())
            .build()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
    }

    private fun setupRecycler() {
        answerRecycler.layoutManager = LinearLayoutManager(requireContext())
        answerRecycler.adapter = adapter
    }
}