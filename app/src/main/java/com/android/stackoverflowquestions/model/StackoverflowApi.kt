package com.android.stackoverflowquestions.model

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface StackoverflowApi {

    @GET("/questions?key=" + "ZiXCZbWaOwnDgpVT9Hx8IA((" + "&order=desc&sort=activity&site=stackoverflow")
    fun getQuestions(@Query("pagesize") pageSize: Int?): Single<QuestionList>
}