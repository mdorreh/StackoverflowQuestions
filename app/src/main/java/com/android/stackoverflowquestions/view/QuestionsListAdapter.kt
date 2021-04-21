package com.android.stackoverflowquestions.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.stackoverflowquestions.databinding.ItemQuestionBinding
import com.android.stackoverflowquestions.model.Question

class QuestionsListAdapter(private var questions: ArrayList<Question>) :
    RecyclerView.Adapter<QuestionsListAdapter.QuestionsViewHolder>() {

    inner class QuestionsViewHolder(private val itemQuestionBinding: ItemQuestionBinding) :
        RecyclerView.ViewHolder(itemQuestionBinding.root) {

        fun bind(question: Question) {
            itemQuestionBinding.apply {
                txtTitle.text = question.title
                txtTitle.setOnClickListener {
                    QuestionDetailsActivity.newInstance(this.root.context, question.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionsViewHolder =
        QuestionsViewHolder(
            ItemQuestionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: QuestionsViewHolder, position: Int) {
        holder.bind(questions[position])
    }

    override fun getItemCount(): Int = questions.size

    fun updateQuestions(updatedQuestions: ArrayList<Question>) {
        questions.clear()
        questions.addAll(updatedQuestions)
        notifyDataSetChanged()
    }
}