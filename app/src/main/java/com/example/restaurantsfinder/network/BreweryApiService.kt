package com.example.restaurantsfinder.network

import com.example.restaurantsfinder.data.Brewery
import com.example.restaurantsfinder.helper.BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface BreweryApiService {

    @GET("?per_page=25")
    fun getListOfBreweries(@Query("page") page: Int): Call<List<Brewery>>

    @GET("?per_page=25")
    fun getBreweriesByCity(@Query("page") page: Int, @Query("by_city") city: String): Call<List<Brewery>>

    @GET("?per_page=25")
    fun getBreweriesByState(@Query("page") page: Int, @Query("by_state") state: String): Call<List<Brewery>>

    @GET("?per_page=25")
    fun getBreweriesByName(@Query("page") page: Int, @Query("by_name") name: String): Call<List<Brewery>>

    companion object {

        fun getRetrofitInstance(): BreweryApiService {

            val interceptor =
                HttpLoggingInterceptor().apply { val level = HttpLoggingInterceptor.Level.BODY }
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(client)
                .build().create(BreweryApiService::class.java)
        }
    }
}