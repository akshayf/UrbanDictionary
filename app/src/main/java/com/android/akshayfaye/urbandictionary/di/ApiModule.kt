package com.android.akshayfaye.urbandictionary.di

import android.content.Context
import com.android.akshayfaye.urbandictionary.utilities.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    internal fun provideRetrofitInterface(okHttpClient : OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Reusable
    internal fun provideHttpClientInterface(cache : Cache, networkCacheInterceptor : Interceptor,
                                            logging : HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .addNetworkInterceptor(networkCacheInterceptor)
            .addInterceptor(logging)
            .addInterceptor(Interceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header("X-RapidAPI-Host", "mashape-community-urban-dictionary.p.rapidapi.com")
                    .header("X-RapidAPI-Key", "813ec51fe3msh1606cc706ef1222p1f89acjsnc268a542b939")
                    .method(original.method(), original.body())
                    .build()
                return@Interceptor chain.proceed(request)
            })
            .build()
    }

    @Provides
    @Reusable
    fun provideCache(context : Context): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        val httpCacheDirectory = File(context.getCacheDir(), "http-cache")
        return Cache(httpCacheDirectory, cacheSize.toLong())
    }

    @Provides
    @Reusable
    fun provideLogging(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        return logging.setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Reusable
    fun provideNetworkCache(): Interceptor {
        return Interceptor { chain ->
            val response = chain.proceed(chain.request())

            val cacheControl = CacheControl.Builder()
                .maxAge(1, TimeUnit.MINUTES)
                .build()

            response.newBuilder()
                .header("Cache-Control", cacheControl.toString())
                .build()
        }

    }

}