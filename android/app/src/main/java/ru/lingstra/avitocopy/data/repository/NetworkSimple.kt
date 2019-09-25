package ru.lingstra.avitocopy.data.repository

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import ru.lingstra.avitocopy.data.prefs.AppPrefs
import ru.lingstra.avitocopy.system.network.NetworkApi
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NetworkSimple @Inject constructor(
    private val api: NetworkApi,
    private val prefs: AppPrefs
) : NetworkProvider {

    private val queriesRelay: PublishRelay<Unit> = PublishRelay.create()
    override fun queries(): Observable<Unit> = queriesRelay.hide()

    override fun loadMore(set: MutableSet<SimplestUser>, level: Int): Boolean =
        set.loadMoreInner(level)

    override fun getFriendItemsFromUrls(urls: Pair<String, String>): Pair<SimplestUser, SimplestUser> =
        getFriendItemFromUrl(urls.first) to getFriendItemFromUrl(urls.second)

    private fun getFriendItemFromUrl(path: String): SimplestUser {
        queriesRelay.accept(Unit)
        return api.userInfo(userId = path.substringAfter("https://vk.com/"), token = prefs.token)
            .map { it.response.first() }
            .map { SimplestUser(it) }
            .delay(300L, TimeUnit.MILLISECONDS)
            .blockingGet()
    }

    private fun MutableSet<SimplestUser>.loadMoreInner(level: Int): Boolean {
        val next = this.firstOrNull { it.shouldCheck && it.level == level }
        next?.let {
            this += getFriendsList(it)
            this.remove(it)
            this.add(it.copy(checked = true))
        }
        return next == null
    }

    private fun getFriendsList(user: SimplestUser): Set<SimplestUser> {
        queriesRelay.accept(Unit)
        return try {
            api.friendsList(user.item.id, token = prefs.token)
                .delay(300L, TimeUnit.MILLISECONDS)
                .blockingGet()
                .response.items
                .map { SimplestUser(item = it, parent = user, level = user.level + 1) }
                .toSet()
        } catch (t: Throwable) {
            Timber.e(t)
            setOf()
        }
    }
}