package com.android.stackoverflowquestions.di

import com.android.stackoverflowquestions.model.StackoverflowApi
import com.android.stackoverflowquestions.model.StackoverflowService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {
    private val BASE_URL="https://api.stackexchange.com/2.2/"

    @Provides
    fun stackoverflowApi():StackoverflowApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(StackoverflowApi::class.java)
    }

    @Provides
    fun stackoverflowService():StackoverflowService = StackoverflowService()

}