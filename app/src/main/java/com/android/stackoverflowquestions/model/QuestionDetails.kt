package com.android.stackoverflowquestions.model

import com.google.gson.annotations.SerializedName

data class QuestionDetails(
    @SerializedName("title") val title: String,
    @SerializedName("question_id") val id: String,
    @SerializedName("body") val body: String
)
