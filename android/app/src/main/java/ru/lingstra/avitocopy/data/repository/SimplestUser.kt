package ru.lingstra.avitocopy.data.repository

data class SimplestUser(
    val id: Int,
    val checked: Boolean = false,
    val parent: SimplestUser? = null,
    val level: Int = 0
) {
    override fun equals(other: Any?): Boolean {
        if (other !is SimplestUser) return false
        return other.id == id
    }

    fun toListChildStart(): List<SimplestUser>{
        val list = mutableListOf<SimplestUser>()
        var cur: SimplestUser? = this
        while (cur != null){
            list.add(cur)
            cur = cur.parent
        }
        return list
    }

    override fun hashCode(): Int {
        return id
    }
}