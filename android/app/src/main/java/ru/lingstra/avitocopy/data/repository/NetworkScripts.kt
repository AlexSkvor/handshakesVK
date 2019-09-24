package ru.lingstra.avitocopy.data.repository

import ru.lingstra.avitocopy.data.prefs.AppPrefs
import ru.lingstra.avitocopy.system.network.NetworkApi
import javax.inject.Inject

class NetworkScripts @Inject constructor(
    private val api: NetworkApi,
    private val prefs: AppPrefs
) : NetworkProvider {

    override fun loadMore(set: MutableSet<SimplestUser>, level: Int): Boolean {
        val queryList = set.asSequence().filter { it.level == level && it.shouldCheck }
            .take(25).toList()
        queryList.let {
            set += getFriendsList(it)
            set.removeAll(it)
            set.addAll(it.map { user -> user.copy(checked = true) })
        }
        return queryList.isEmpty()
    }

    override fun getFriendItemsFromUrls(urls: Pair<String, String>): Pair<SimplestUser, SimplestUser> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun getFriendsList(users: List<SimplestUser>): Set<SimplestUser> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}