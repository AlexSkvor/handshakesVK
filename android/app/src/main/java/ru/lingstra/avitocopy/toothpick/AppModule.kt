package ru.lingstra.avitocopy.toothpick

import android.content.Context
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import ru.lingstra.avitocopy.domain.common.SearchFilter
import ru.lingstra.avitocopy.system.*
import ru.lingstra.avitocopy.system.network.*
import ru.lingstra.avitocopy.system.schedulers.AppSchedulers
import ru.lingstra.avitocopy.system.schedulers.SchedulersProvider
import toothpick.config.Module

class AppModule(context: Context, serverPath: String) : Module() {
    init {
        val filter = SearchFilter()
        bind(Context::class.java).toInstance(context)
        bind(ResourceManager::class.java).singletonInScope()
        bind(SystemMessage::class.java).toInstance(SystemMessage())
        bind(SchedulersProvider::class.java).toInstance(AppSchedulers())
        bind(String::class.java).withName(DefaultServerPath::class.java).toInstance(serverPath)
        bind(SearchFilter::class.java).withName(FilterSearch::class.java).toInstance(filter)

        bind(Interceptor::class.java)
            .withName(SuccessInterceptor::class.java)
            .to(NetworkInterceptor::class.java)
            .singletonInScope()

        bind(OkHttpClient::class.java)
            .withName(SuccessOkHttpClient::class.java)
            .toProvider(HttpClientProvider::class.java)
            .singletonInScope()

        bind(NetworkApi::class.java)
            .toProvider(ApiProvider::class.java)
            .singletonInScope()
    }
}