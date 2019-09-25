package ru.lingstra.avitocopy.data.repository

import io.reactivex.Observable

interface NetworkProvider {
    fun loadMore(set: MutableSet<SimplestUser>, level: Int): Boolean
    fun getFriendItemsFromUrls(urls: Pair<String, String>): Pair<SimplestUser, SimplestUser>
    fun queries(): Observable<Unit>
}