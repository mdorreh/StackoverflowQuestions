package com.android.stackoverflowquestions.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.android.stackoverflowquestions.databinding.ActivityQuestionDetailsBinding
import com.android.stackoverflowquestions.viewmodel.QuestionDetailsViewModel

class QuestionDetailsActivity : AppCompatActivity() {

    private lateinit var questionDetailsViewModel: QuestionDetailsViewModel
    private lateinit var questionId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val detailBinding = ActivityQuestionDetailsBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)
        questionId = intent.getStringExtra(EXTRA_QUESTION_ID)!!
        questionDetailsViewModel = ViewModelProvider(this).get(QuestionDetailsViewModel::class.java)
        questionDetailsViewModel.fetchQuestionDetails(questionId)
        detailBinding.apply {
            questionDetailsViewModel.question.observe(this@QuestionDetailsActivity, { question ->
                details.text = question.body
            })
        }
    }

    companion object {
        private const val EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID"
        fun newInstance(context: Context, questionId: String) {
            val intent = Intent(context, QuestionDetailsActivity::class.java)
            intent.putExtra(EXTRA_QUESTION_ID, questionId)
            context.startActivity(intent)
        }
    }

}