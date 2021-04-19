package com.android.stackoverflowquestions.model

import com.android.stackoverflowquestions.di.DaggerApiComponent
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class StackoverflowService {

    @Inject
    lateinit var api: StackoverflowApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getQuestions():Single<QuestionList>{
        return api.getQuestions(20)
    }
}