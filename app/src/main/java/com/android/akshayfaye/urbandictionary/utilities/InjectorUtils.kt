package com.android.akshayfaye.urbandictionary.utilities

import android.content.Context
import com.android.akshayfaye.urbandictionary.DictionaryViewModelFactory
import com.android.akshayfaye.urbandictionary.network.ApiService
import com.android.akshayfaye.urbandictionary.network.RetrofitClient
import com.android.akshayfaye.urbandictionary.data.DictionaryRepository

object InjectorUtils {

    fun provideAcronymsViewModelFactory(context : Context) : DictionaryViewModelFactory {

        val acronymsApiService  = RetrofitClient.getRetrofitClient(context).create(
            ApiService::class.java)
        val acronymsRepository = DictionaryRepository.getInstance(acronymsApiService)
        return DictionaryViewModelFactory(acronymsRepository)
    }
}