package com.android.akshayfaye.urbandictionary

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.android.akshayfaye.urbandictionary.data.Definitions
import com.android.akshayfaye.urbandictionary.data.Dictionary
import com.android.akshayfaye.urbandictionary.data.DictionaryRepository
import com.android.akshayfaye.urbandictionary.network.ApiService
import org.junit.Assert
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DictionaryRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var apiService: ApiService

    lateinit var dictionaryRepository: DictionaryRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.dictionaryRepository = DictionaryRepository(apiService)
    }

    @Test
    fun fetchFullForms_positiveResponse(){

        val mutableLiveData = this.dictionaryRepository.getDefinition(ArgumentMatchers.anyString())

        assertNotNull(mutableLiveData)
    }

}
