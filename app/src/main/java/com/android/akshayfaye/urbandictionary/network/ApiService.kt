package com.android.akshayfaye.urbandictionary.network

import com.android.akshayfaye.urbandictionary.data.Dictionary
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * ApiService interface for initiating server calls
 */
interface ApiService {

    @GET("define")
    fun getDefinition(@Query("term") searchString: String): Call<Dictionary>?
}