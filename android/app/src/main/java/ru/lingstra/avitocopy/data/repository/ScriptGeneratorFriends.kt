package ru.lingstra.avitocopy.data.repository

import javax.inject.Inject

class ScriptGeneratorFriends @Inject constructor() {

    fun generateForList(users: List<SimplestUser>): String =
        "var listSize = ${users.size};\n" +
                "var idList = ${users.map { it.item.id }};\n" +
                "var answer = [];\n" +
                "while(listSize != 0){\n" +
                "\tvar k = API.friends.get({\"user_id\":idList[listSize - 1],\"count\":\"15000\", \"name_case\":\"ru\", \"fields\":\"domain, photo_100\", \"v\":\"5.101\"});\n" +
                "\tif(k){answer.push({\"parent\": idList[listSize - 1], \"friends\": k});}\n" +
                "\tlistSize = listSize - 1;\n" +
                "}\n" +
                "return answer;"
}