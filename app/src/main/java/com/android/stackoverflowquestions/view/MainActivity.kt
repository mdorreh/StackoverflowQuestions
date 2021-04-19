package com.android.stackoverflowquestions.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.stackoverflowquestions.databinding.ActivityMainBinding
import com.android.stackoverflowquestions.viewmodel.QuestionsListViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var questionsListViewModel: QuestionsListViewModel
    private val questionsAdapter = QuestionsListAdapter(arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        questionsListViewModel = ViewModelProvider(this).get(QuestionsListViewModel::class.java)
        questionsListViewModel.refresh()
        mainBinding.apply {
            questionsList.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = questionsAdapter
            }

            swipeRefreshLayout.apply {
                setOnRefreshListener {
                    this.isRefreshing = false
                    questionsListViewModel.refresh()
                }
            }

            questionsListViewModel.questions.observe(this@MainActivity, { questions ->
                questionsList.visibility = View.VISIBLE
                questions?.let {
                    questionsAdapter.updateQuestions(ArrayList(it))
                }
            })

            questionsListViewModel.questionLoadError.observe(
                this@MainActivity,
                { isError ->
                    isError?.let {
                        listError.visibility = if (it) View.VISIBLE else View.GONE
                    }
                })

            questionsListViewModel.loading.observe(this@MainActivity, { isLoading ->
                isLoading?.let {
                    loadingList.visibility = if (it) View.VISIBLE else View.GONE
                    if (it) {
                        listError.visibility = View.GONE
                        questionsList.visibility = View.GONE
                    }
                }
            })
        }
    }
}