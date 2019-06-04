package com.android.akshayfaye.urbandictionary.di

import com.android.akshayfaye.urbandictionary.data.DictionaryRepository
import com.android.akshayfaye.urbandictionary.network.ApiService
import com.android.akshayfaye.urbandictionary.ui.DictionaryViewModel
import com.android.akshayfaye.urbandictionary.ui.DictionaryViewModelFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class FactoryModule {

    @Provides
    @Singleton
    internal fun provideViewModelFactory(retrofit: Retrofit) : DictionaryViewModelFactory {

        val apiService  = retrofit.create(ApiService::class.java)
        val repository = DictionaryRepository.getInstance(apiService)
        return DictionaryViewModelFactory(repository)
    }
}