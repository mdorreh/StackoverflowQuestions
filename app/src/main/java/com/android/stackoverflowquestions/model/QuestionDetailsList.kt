package com.android.stackoverflowquestions.model

import com.google.gson.annotations.SerializedName

data class QuestionDetailsList(@SerializedName("items") val questions: List<QuestionDetails>) {
    val question: QuestionDetails get() = questions[0]
}