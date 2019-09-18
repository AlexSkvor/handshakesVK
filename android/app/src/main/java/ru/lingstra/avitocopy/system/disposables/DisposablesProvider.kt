package ru.lingstra.avitocopy.system.disposables

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

interface DisposablesProvider {
    val compositeDisposable: CompositeDisposable
    fun clear()
    fun Disposable.bind()
}