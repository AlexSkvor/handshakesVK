package ru.lingstra.avitocopy.data.repository

import io.reactivex.Observable
import io.reactivex.Single
import ru.lingstra.avitocopy.data.prefs.AppPrefs
import ru.lingstra.avitocopy.domain.hand_shakes.User
import ru.lingstra.avitocopy.system.network.NetworkApi
import ru.lingstra.avitocopy.system.schedulers.SchedulersProvider
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HandShakesRepository @Inject constructor(
    private val api: NetworkApi,
    private val scheduler: SchedulersProvider,
    private val prefs: AppPrefs
) {

    private val length = 3

    fun searchHandShakes(firstUrl: String, secondUrl: String): Observable<List<User>> =
        Single.just(Unit)
            .map { fullWork(firstUrl, secondUrl) }
            .map { getPathFromIds(it.first, it.second) }
            .toObservable()
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())

    private fun fullWork(firstUrl: String, secondUrl: String): Pair<Int, Int> {
        val firstId =
            api.getVkId(userId = firstUrl.substringAfter("https://vk.com/"), token = prefs.token)
                .map { it.response.first().id }
                .delay(300L, TimeUnit.MILLISECONDS)
                .blockingGet()
        val secondId =
            api.getVkId(userId = secondUrl.substringAfter("https://vk.com/"), token = prefs.token)
                .map { it.response.first().id }
                .delay(300L, TimeUnit.MILLISECONDS)
                .blockingGet()
        return firstId to secondId
    }

    private fun getPathFromIds(firstId: Int, secondId: Int): List<User> {
        val firstUser = SimplestUser(id = firstId)
        val forwardSet = mutableSetOf(firstUser)
        val secondUser = SimplestUser(id = secondId)
        val backwardSet = mutableSetOf(secondUser)
        var i = 0
        while (i < length) {
            forwardSet.intersect(backwardSet).firstOrNull()?.let { common ->
                return buildPath(
                    forwardSet.first { it.id == common.id },
                    backwardSet.first { it.id == common.id })
            }
            val nextForward: SimplestUser? = forwardSet.firstOrNull { !it.checked && it.level == i }
            nextForward?.let {
                forwardSet += try {
                    api.getFriendsForUser(it.id, token = prefs.token)
                        .delay(300L, TimeUnit.MILLISECONDS)
                        .blockingGet()
                        .response.items
                        .map { id -> SimplestUser(id = id, parent = it, level = it.level + 1) }
                        .toSet()
                } catch (t: Throwable) {
                    Timber.e(t)
                    setOf<SimplestUser>()
                }
                forwardSet.remove(it)
                forwardSet.add(it.copy(checked = true))
            }


            forwardSet.intersect(backwardSet).firstOrNull()?.let { common ->
                return buildPath(
                    forwardSet.first { it.id == common.id },
                    backwardSet.first { it.id == common.id })
            }
            val nextBackward: SimplestUser? =
                backwardSet.firstOrNull { !it.checked && it.level == i }
            nextBackward?.let {
                backwardSet += try {
                    api.getFriendsForUser(it.id, token = prefs.token)
                        .delay(300L, TimeUnit.MILLISECONDS)
                        .blockingGet()
                        .response.items
                        .map { id -> SimplestUser(id = id, parent = it, level = it.level + 1) }
                        .toSet()
                } catch (t: Throwable) {
                    Timber.e(t)
                    setOf<SimplestUser>()
                }
                backwardSet.remove(it)
                backwardSet.add(it.copy(checked = true))
            }
            if (nextBackward == null && nextForward == null) i++
        }
        return emptyList()
    }

    private fun buildPath(forward: SimplestUser, backward: SimplestUser): List<User> {
        val answer = forward.toListChildStart().reversed() + backward.toListChildStart()
        val ids = answer.distinct().map { it.id }
        return idListToUserList(ids)
    }

    private fun idListToUserList(list: List<Int>): List<User> {
        val answer = mutableListOf<User>()
        list.forEach { id ->
            answer += api.getVkId(userId = id.toString(), token = prefs.token)
                .map { it.response.first() }
                .map {
                    User(
                        name = it.first_name,
                        surname = it.last_name,
                        id = it.id.toString(),
                        photo = it.photo_50
                    )
                }
                .delay(300L, TimeUnit.MILLISECONDS)
                .blockingGet()
        }
        return answer
    }
}