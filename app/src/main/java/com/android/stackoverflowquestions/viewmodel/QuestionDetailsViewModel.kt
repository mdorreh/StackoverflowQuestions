package com.android.stackoverflowquestions.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.stackoverflowquestions.di.DaggerApiComponent
import com.android.stackoverflowquestions.model.QuestionDetails
import com.android.stackoverflowquestions.model.QuestionDetailsList
import com.android.stackoverflowquestions.model.StackoverflowService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class QuestionDetailsViewModel : ViewModel() {

    @Inject
    lateinit var stackoverflowService: StackoverflowService

    private val disposable = CompositeDisposable()
    val question = MutableLiveData<QuestionDetails>()

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun fetchQuestionDetails(questionId: String) {
        disposable.add(
            stackoverflowService.getQuestionDetails(questionId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<QuestionDetailsList>() {
                    override fun onSuccess(t: QuestionDetailsList?) {
                        question.value = t?.question!!
                    }

                    override fun onError(e: Throwable?) {
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}