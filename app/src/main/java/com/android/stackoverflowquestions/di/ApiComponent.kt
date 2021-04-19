package com.android.stackoverflowquestions.di

import com.android.stackoverflowquestions.model.StackoverflowService
import com.android.stackoverflowquestions.viewmodel.QuestionsListViewModel
import dagger.Component

@Component(modules=[ApiModule::class])
interface ApiComponent {

    fun inject(service:StackoverflowService)
    fun inject(service:QuestionsListViewModel)
}