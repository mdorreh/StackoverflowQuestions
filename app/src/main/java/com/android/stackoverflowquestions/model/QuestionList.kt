package com.android.stackoverflowquestions.model

import com.google.gson.annotations.SerializedName

data class QuestionList(@SerializedName("items") val questions: List<Question>)