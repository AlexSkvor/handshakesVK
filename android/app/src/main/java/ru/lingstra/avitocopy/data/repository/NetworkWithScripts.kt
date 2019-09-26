package ru.lingstra.avitocopy.data.repository
/*
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import ru.lingstra.avitocopy.data.prefs.AppPrefs
import ru.lingstra.avitocopy.system.network.NetworkApi
import ru.lingstra.avitocopy.system.network.QueriesTimeController
import timber.log.Timber
import javax.inject.Inject

class NetworkWithScripts @Inject constructor(
    private val api: NetworkApi,
    private val prefs: AppPrefs,
    private val scriptGenerator: ScriptGeneratorFriends,
    private val timeController: QueriesTimeController
) : NetworkProvider {

    private val queriesRelay: PublishRelay<Unit> = PublishRelay.create()
    override fun queries(): Observable<Unit> = queriesRelay.hide()

    override fun loadMore(set: MutableSet<SimplestUser>, level: Int): Boolean {
        val queryList = set.asSequence().filter { it.level == level && it.shouldCheck }
            .take(25).toList()
        if (queryList.isEmpty()) return true
        return queryList.let {
            set += getFriendsList(it)
            set.removeAll(it)
            set.addAll(it.map { user -> user.copy(checked = true) })
            false
        }
    }

    override fun getFriendItemsFromUrls(urls: Pair<String, String>): Pair<SimplestUser, SimplestUser> =
        getFriendItemFromUrl(urls.first) to getFriendItemFromUrl(urls.second)

    private fun getFriendItemFromUrl(path: String): SimplestUser {
        return timeController.nextQuery()
            .also { queriesRelay.accept(Unit) }
            .andThen(api.userInfo(
            userId = path.substringAfter("https://vk.com/"),
            token = prefs.token
        )
            .map { it.response.first() }
            .map { SimplestUser(it) }
        ).blockingGet()
    }

    private fun getFriendsList(users: List<SimplestUser>): Set<SimplestUser> {
        val script = scriptGenerator.generateForList(users)
        return try {
            timeController.nextQuery()
                .also { queriesRelay.accept(Unit) }
                .andThen(api.executeScript(script, token = prefs.token))
                .blockingGet()
                .response.map {
                it.friends.items.map { friend ->
                    SimplestUser(
                        item = friend,
                        parent = users.first { user -> user.item.id == it.parent },
                        level = users.first().level + 1
                    )
                }
            }.flatten().toSet()
        } catch (t: Throwable) {
            Timber.e(t)
            setOf()
        }
    }
}*/