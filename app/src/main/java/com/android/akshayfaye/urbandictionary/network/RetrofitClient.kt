package com.android.akshayfaye.urbandictionary.network

import android.content.Context
import com.android.akshayfaye.urbandictionary.utilities.BASE_URL
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * RetrofitClient for network calls
 * Sets cache, header and logging interceptors
 */
object RetrofitClient {

    internal fun getRetrofitClient(context: Context) : Retrofit {

        // Create a cache object
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        val httpCacheDirectory = File(context.getCacheDir(), "http-cache")
        val cache = Cache(httpCacheDirectory, cacheSize.toLong())

        // create a network cache interceptor, setting the max age to 1 minute
        val networkCacheInterceptor = Interceptor { chain ->
            val response = chain.proceed(chain.request())

            val cacheControl = CacheControl.Builder()
                .maxAge(1, TimeUnit.MINUTES)
                .build()

            response.newBuilder()
                .header("Cache-Control", cacheControl.toString())
                .build()
        }

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        // Create the httpClient, configure it
        // with cache, network cache interceptor and logging interceptor
        val httpClient = OkHttpClient.Builder()
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

        // Create the Retrofit with the httpClient
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }
}