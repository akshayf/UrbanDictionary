package com.android.akshayfaye.urbandictionary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.akshayfaye.urbandictionary.data.DictionaryRepository

/**
 * Factory class for View Model
 * @param dictionaryRepository
 * @return Instance of Factory
 */
class DictionaryViewModelFactory(private val dictionaryRepository : DictionaryRepository)
    : ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DictionaryViewModel(dictionaryRepository) as T
    }

}