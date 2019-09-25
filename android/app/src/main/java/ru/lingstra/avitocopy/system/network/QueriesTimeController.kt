package ru.lingstra.avitocopy.system.network

import java.util.*
import javax.inject.Inject

class QueriesTimeController @Inject constructor() {

    private val queriesPerSecond: Int = 3
    private val queue: Queue<Long> = Queue(3)

    fun nextQuery() {
        val time = Date().time
        if (queue.size < queriesPerSecond) {
            return queue.put(time)
        }
        if (Date().time - queue.firstOrDefault(0) > 1001L) {
            return queue.put(time)
        }
        val delay = time - queue.firstOrDefault(0)
        assert(delay > 0)
        Thread.sleep(delay)
        return queue.put(time)
    }
}