package ru.lingstra.avitocopy.data.repository

import ru.lingstra.avitocopy.domain.hand_shakes.FriendItem
import ru.lingstra.avitocopy.domain.hand_shakes.Response

data class IdFromRefServerResponse(
    val response: List<FriendItem>
)

data class ScriptFriendsResponse(
    val response: List<FriendsListWithParent>
)

data class FriendsListWithParent(
    val parent: Int,
    val friends: Response
)