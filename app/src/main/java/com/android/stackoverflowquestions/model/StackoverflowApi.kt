package com.android.stackoverflowquestions.model

import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StackoverflowApi {

    @GET("/questions?key=" + "ZiXCZbWaOwnDgpVT9Hx8IA((" + "&order=desc&sort=activity&site=stackoverflow")
    fun getQuestions(@Query("pagesize") pageSize: Int?): Single<QuestionList>

    @GET("/questions/{questionId}?key=" + "ZiXCZbWaOwnDgpVT9Hx8IA((" + "&site=stackoverflow&filter=withbody")
    fun getQuestionDetails(@Path("questionId") questionId: String?): Single<QuestionDetailsList>
}