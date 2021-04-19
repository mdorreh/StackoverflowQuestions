package com.android.stackoverflowquestions.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.stackoverflowquestions.di.DaggerApiComponent
import com.android.stackoverflowquestions.model.Question
import com.android.stackoverflowquestions.model.QuestionList
import com.android.stackoverflowquestions.model.StackoverflowService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class QuestionsListViewModel : ViewModel() {

    @Inject
    lateinit var stackoverflowService: StackoverflowService
    private val disposable = CompositeDisposable()
    val questions = MutableLiveData<List<Question>>()
    val questionLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun refresh() {
        fetchQuestions()
    }

    private fun fetchQuestions() {
        loading.value = true
        disposable.add(
            stackoverflowService.getQuestions()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<QuestionList>() {
                    override fun onSuccess(t: QuestionList?) {
                        questions.value = t?.questions!!
                        questionLoadError.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable?) {
                        questionLoadError.value = true
                        loading.value = false
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}