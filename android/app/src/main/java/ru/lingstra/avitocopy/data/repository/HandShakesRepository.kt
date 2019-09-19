package ru.lingstra.avitocopy.data.repository

import io.reactivex.Observable
import ru.lingstra.avitocopy.data.prefs.AppPrefs
import ru.lingstra.avitocopy.system.network.NetworkApi
import ru.lingstra.avitocopy.system.schedulers.SchedulersProvider
import javax.inject.Inject

class HandShakesRepository @Inject constructor(
    private val api: NetworkApi,
    private val scheduler: SchedulersProvider,
    private val prefs: AppPrefs
) {
    fun searchHandShakes(firstUrl: String, secondUrl: String): Observable<List<Int>> =
        api.getFriendsForUser(userId = 150973989, token = prefs.token)
            .map { it.response.items }
            .toObservable()
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
}