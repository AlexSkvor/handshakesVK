package ru.lingstra.avitocopy.ui.list

import android.annotation.SuppressLint
import coil.api.load
import kotlinx.android.synthetic.main.item_answer.view.*
import ru.lingstra.avitocopy.R
import ru.lingstra.avitocopy.domain.hand_shakes.User
import ru.lingstra.avitocopy.ui.utils.delegate.DelegateAdapter

class UserListAdapter : DelegateAdapter<User>() {

    @SuppressLint("SetTextI18n")
    override fun onBind(item: User, holder: DelegateViewHolder) =
        with(holder.itemView) {
            avatar.load(item.photo)
            answerIdField.text = item.name + " " + item.surname
        }

    override fun getLayoutId(): Int = R.layout.item_answer
    override fun isForViewType(items: List<*>, position: Int): Boolean = items[position] is User
}