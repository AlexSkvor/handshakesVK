package ru.lingstra.avitocopy.data.repository

import io.reactivex.Observable
import io.reactivex.Single
import ru.lingstra.avitocopy.domain.hand_shakes.User
import ru.lingstra.avitocopy.system.schedulers.SchedulersProvider
import javax.inject.Inject

class HandShakesRepository @Inject constructor(
    private val network: NetworkProvider,
    private val scheduler: SchedulersProvider
) {

    fun queries(): Observable<Unit> = network.queries().observeOn(scheduler.ui())

    private val length = 3

    fun searchHandShakes(firstUrl: String, secondUrl: String): Observable<List<User>> =
        Single.just(Unit)
            .map { network.getFriendItemsFromUrls(firstUrl to secondUrl) }
            .map { getPathFromIds(it.first, it.second) }
            .toObservable()
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())

    private fun getPathFromIds(firstUser: SimplestUser, secondUser: SimplestUser): List<User> {
        val forwardSet = mutableSetOf(firstUser)
        val backwardSet = mutableSetOf(secondUser)
        var i = 0
        while (i < length) {
            forwardSet.buildPath(backwardSet)?.let { return it }
            val finishedLevelForward = network.loadMore(forwardSet, i)
            forwardSet.buildPath(backwardSet)?.let { return it }
            val finishedLevelBackward = network.loadMore(backwardSet, i)
            if (finishedLevelForward && finishedLevelBackward) i++
        }
        return emptyList()
    }

    private fun Set<SimplestUser>.buildPath(second: Set<SimplestUser>): List<User>? {
        this.map { it.item.id }.intersect(second.map { it.item.id }).firstOrNull()
            ?.let { id ->
                val answer = this.first { it.item.id == id }.toListChildStart().reversed() +
                        second.first { it.item.id == id }.toListChildStart()
                return answer.distinct().map { User(it) }
            }
        return null
    }
}