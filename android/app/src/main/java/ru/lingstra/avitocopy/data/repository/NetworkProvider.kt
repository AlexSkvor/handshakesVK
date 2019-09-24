package ru.lingstra.avitocopy.data.repository

interface NetworkProvider {
    fun loadMore(set: MutableSet<SimplestUser>, level: Int): Boolean
    fun getFriendItemsFromUrls(urls: Pair<String, String>): Pair<SimplestUser, SimplestUser>
}