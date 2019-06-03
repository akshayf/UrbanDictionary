package com.android.akshayfaye.urbandictionary.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.akshayfaye.urbandictionary.data.DictionaryRepository

/**
 * ViewModel class as interface between multiple Views and DictionaryRepository
 * @param repository
 */
class DictionaryViewModel(private val repository: DictionaryRepository) : ViewModel(){

    fun getDefinition(searchString: String)  = repository.getDefinition(searchString)

    val searchStringData = MutableLiveData<String>()

    fun setSearchString(searchString: String) {
        searchStringData.value = searchString
    }

}