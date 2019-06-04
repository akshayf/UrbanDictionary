package com.android.akshayfaye.urbandictionary

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.akshayfaye.urbandictionary.data.Definitions
import com.android.akshayfaye.urbandictionary.data.Dictionary
import com.android.akshayfaye.urbandictionary.data.DictionaryRepository
import com.android.akshayfaye.urbandictionary.ui.DictionaryViewModel
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DictionaryViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var dictionaryRepository: DictionaryRepository

    lateinit var dictionaryViewModel: DictionaryViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.dictionaryViewModel = DictionaryViewModel(this.dictionaryRepository)
    }

    @Test
    fun fetchFullForms_positiveResponse(){

        // Mock API response
        Mockito.`when`(this.dictionaryRepository.getDefinition(ArgumentMatchers.anyString())).thenAnswer {
            return@thenAnswer MutableLiveData(ArgumentMatchers.any<Dictionary>())
        }

        val mutableLiveData = this.dictionaryViewModel.searchDefinitions(ArgumentMatchers.anyString())

        assertNotNull(mutableLiveData)
        assertEquals(mutableLiveData.mData.value, ArgumentMatchers.any<Dictionary>())
    }
}