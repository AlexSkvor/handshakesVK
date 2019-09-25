package ru.lingstra.avitocopy.data.repository

import javax.inject.Inject

class ScriptGeneratorFriends @Inject constructor() {

    fun generateForList(users: List<SimplestUser>): String {
        var script = ""
        users.forEach {
            script = applyUser(it.item.id, script)
        }
        script += "return ["
        users.forEach { script += "userFormat${it.item.id}," }
        script = script.removeSuffix(",")
        return "$script];"
    }

    private fun applyUser(userId: Int, script: String): String {
        val initiation = "var userId$userId = API.friends.get({\"user_id\":\"$userId\"," +
                "\"count\":\"15000\", \"name_case\":\"ru\", \"fields\":\"domain, photo_100\", \"v\":\"5.101\"});\n"
        val ansFormat = "var userFormat$userId = {\"parent\": \"$userId\", " +
                "\"friends\": userId$userId};\n"
        return script + initiation + ansFormat
    }
}