package ru.lingstra.avitocopy.system.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class NetworkInterceptor @Inject constructor(
    private val timeController: QueriesTimeController
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {
        chain ?: throw IOException("Failed to interceptor chain")

        val builder = chain.request().newBuilder()
        if (chain.request().method() == "GET") {
            builder.addHeader("X-Requested-With", "XMLHttpRequest")
        }

        timeController.nextQuery()
        return chain.proceed(builder.build())
    }
}