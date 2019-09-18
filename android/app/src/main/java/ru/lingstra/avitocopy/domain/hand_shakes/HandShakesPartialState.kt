package ru.lingstra.avitocopy.domain.hand_shakes

sealed class HandShakesPartialState(private val logMessage: String) {

    data class Answer(val list: List<Int>): HandShakesPartialState("Answer $list")
    data class Loading(val show: Boolean): HandShakesPartialState("Loading $show")
    data class Error(val error: Throwable): HandShakesPartialState("Error $error")

    override fun toString(): String = logMessage
    fun partial() = this
}