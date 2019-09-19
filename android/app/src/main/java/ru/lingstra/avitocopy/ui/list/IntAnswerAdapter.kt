package ru.lingstra.avitocopy.ui.list

import kotlinx.android.synthetic.main.item_answer.view.*
import ru.lingstra.avitocopy.R
import ru.lingstra.avitocopy.alsoPrintDebug
import ru.lingstra.avitocopy.ui.utils.delegate.DelegateAdapter

class IntAnswerAdapter : DelegateAdapter<Int>() {

    override fun onBind(item: Int, holder: DelegateViewHolder) =
        with(holder.itemView) {
            answerIdField.text = item.toString().alsoPrintDebug("AAAAAAAAAAAA")
        }

    override fun getLayoutId(): Int = R.layout.item_answer
    override fun isForViewType(items: List<*>, position: Int): Boolean = items[position] is Int
}