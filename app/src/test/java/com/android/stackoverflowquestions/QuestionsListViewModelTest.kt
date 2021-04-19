package com.android.stackoverflowquestions

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.stackoverflowquestions.model.QuestionList
import com.android.stackoverflowquestions.model.StackoverflowService
import com.android.stackoverflowquestions.viewmodel.QuestionsListViewModel
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.internal.schedulers.ExecutorScheduler
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit

class QuestionsListViewModelTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var stackoverflowService: StackoverflowService

    @InjectMocks
    var listViewModel = QuestionsListViewModel()

    private var testSingle: Single<QuestionList>? = null

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getQuestionsSuccess() {
        val questionList = QuestionList(listOf())
        testSingle = Single.just(questionList)
        Mockito.`when`(stackoverflowService.getQuestions()).thenReturn(testSingle)

        listViewModel.refresh()
        Assert.assertEquals(0, listViewModel.questions.value?.size)
        Assert.assertEquals(false, listViewModel.questionLoadError.value)
        Assert.assertEquals(false, listViewModel.loading.value)
    }

    @Test
    fun getCountriesFailure() {
        testSingle = Single.error(Throwable())
        Mockito.`when`(stackoverflowService.getQuestions()).thenReturn(testSingle)

        listViewModel.refresh()
        Assert.assertEquals(true, listViewModel.questionLoadError.value)
        Assert.assertEquals(false, listViewModel.loading.value)
    }

    @Before
    fun setUpRxSchedulers() {
        val immediate = object : Scheduler() {

            override fun scheduleDirect(run: Runnable?, delay: Long, unit: TimeUnit?): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker({ it.run() }, true, true)
            }
        }
        RxJavaPlugins.setInitIoSchedulerHandler { immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
    }
}