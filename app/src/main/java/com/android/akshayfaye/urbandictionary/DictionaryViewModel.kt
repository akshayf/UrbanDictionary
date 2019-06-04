package com.android.akshayfaye.urbandictionary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.android.akshayfaye.urbandictionary.data.Dictionary
import com.android.akshayfaye.urbandictionary.data.DictionaryRepository

/**
 * ViewModel class as interface between multiple Views and DictionaryRepository
 * @param repository
 */
class DictionaryViewModel(private val repository: DictionaryRepository) : ViewModel(){

    var mData : LiveData<Dictionary>

    private val query = MutableLiveData<String>()

    init {
        mData =  Transformations.switchMap(
                query,
                ::findDefinitions
        )
    }
    fun searchDefinitions(queryString: String) = apply { query.value = queryString }

    fun findDefinitions(query: String) =  repository.getDefinition(query)
}