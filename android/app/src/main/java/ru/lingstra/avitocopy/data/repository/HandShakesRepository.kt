package ru.lingstra.avitocopy.data.repository

import io.reactivex.Observable
import io.reactivex.Single
import ru.lingstra.avitocopy.data.prefs.AppPrefs
import ru.lingstra.avitocopy.system.network.NetworkApi
import ru.lingstra.avitocopy.system.schedulers.SchedulersProvider
import javax.inject.Inject

class HandShakesRepository @Inject constructor(
    private val api: NetworkApi,
    private val scheduler: SchedulersProvider,
    private val prefs: AppPrefs
) {

    private val length = 3

    fun searchHandShakes(firstUrl: String, secondUrl: String): Observable<List<Int>> =
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
                .blockingGet()
        val secondId =
            api.getVkId(userId = secondUrl.substringAfter("https://vk.com/"), token = prefs.token)
                .map { it.response.first().id }
                .blockingGet()
        return firstId to secondId
    }

    private fun getPathFromIds(firstId: Int, secondId: Int): List<Int> {
        val firstUser = SimplestUser(id = firstId)
        val forwardSet = setOf(firstUser)
        val secondUser = SimplestUser(id = secondId)
        val backwardSet = setOf(secondUser)
        var i = 0
        while (i < length) {
            val nextForward: SimplestUser? = forwardSet.firstOrNull { !it.checked && it.level == i }
            nextForward?.let { }//TODO
            val nextBackward: SimplestUser? =
                backwardSet.firstOrNull { !it.checked && it.level == i }
            nextBackward?.let { }//TODO
            if (nextBackward == null && nextForward == null) i++
            //TODO check if we found answer!
        }
        return emptyList()
    }
}