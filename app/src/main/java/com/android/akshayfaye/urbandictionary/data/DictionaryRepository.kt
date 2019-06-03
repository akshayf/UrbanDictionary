package com.android.akshayfaye.urbandictionary.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.akshayfaye.urbandictionary.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Repository to communicate between ApiService and Model class
 */
class DictionaryRepository(val apiService: ApiService){

    val TAG : String = "DictionaryRepository";

    companion object{

        @Volatile private var instance: DictionaryRepository? = null

        fun getInstance(apiService: ApiService) =
                instance ?: synchronized(this){
                    instance
                        ?: DictionaryRepository(apiService).also { instance = it }
                }
    }

    /**
     * Fetches the response from ApiService and convert it in to MutableLiveData
     * @param searchString
     * @return MutableLiveData
     */
    fun getDefinition(searchString: String) : MutableLiveData<Dictionary>{

        val data = MutableLiveData<Dictionary>()
        val call = apiService.getDefinition(searchString)

        call?.enqueue(object : Callback<Dictionary> {
            override fun onResponse(call: Call<Dictionary>?, response: Response<Dictionary>?) {
                data.postValue(response?.body())
                Log.e(TAG, "Response - " + response);
            }

            override fun onFailure(call: Call<Dictionary>?, t: Throwable?) {
                data.value = null
                Log.e(TAG, "Response --- " + null);
            }
        })

        return data
    }

}