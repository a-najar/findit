package dev.youdroid.findit.pages.quiz

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.youdroid.findit.domain.entities.Question
import dev.youdroid.findit.domain.usecases.GetQuizUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class QuizGameViewModel @Inject constructor(private val getQuizUseCase: GetQuizUseCase) :
    ViewModel() {

    private val uiStateFlow = MutableStateFlow(UiState())

    val uiState: StateFlow<UiState> = uiStateFlow.asStateFlow()


    init {
        viewModelScope.launch {
            runCatching { getQuizUseCase() }
                .onSuccess { buildInitState(it) }
                .onFailure { it.printStackTrace() }
        }
    }

    private fun buildInitState(questions: List<Question>) {
        uiStateFlow += UiState(
            questions = questions, currentQuestions = questions.firstOrNull(), currentIndex = 0
        )
    }

    fun submitAndNext(currentAnswer: Int, state: UiState) {
        val nextQuestionIndex = state.currentIndex + 1
        val isLastQuestion = nextQuestionIndex == state.questions.size - 1
        uiStateFlow += state.copy(
            currentQuestions = if (isLastQuestion) state.questions.last() else state.questions[nextQuestionIndex],
            currentIndex = if (isLastQuestion) state.questions.lastIndex else nextQuestionIndex,
            answers = setAnswers(state, currentAnswer),
            progress = nextQuestionIndex.toFloat() / state.questions.size,
            isCompleted = isLastQuestion,
            score = calculateMark(state),
            moveToNextCompleteScreen = false
        )
    }

    fun submitCompleted(currentAnswer: Int, state: UiState) {
        uiStateFlow += state.copy(
            currentQuestions = state.questions.last(),
            currentIndex = state.questions.lastIndex,
            answers = setAnswers(state, currentAnswer),
            progress = state.questions.lastIndex.toFloat() / state.questions.size,
            isCompleted = true,
            score = calculateMark(state),
            moveToNextCompleteScreen = true
        )
    }

    private fun calculateMark(state: UiState): Int {
        var score = 0
        Log.d("Answers:", state.answers.toTypedArray().contentDeepToString())
        state.answers.forEachIndexed { index, i ->

            Log.d("Answers:", state.questions[index].correctAnswerIndex.toString())
            Log.d("Answers:", i.toString())

            if (state.questions[index].correctAnswerIndex == i) {
                score += 1
            }
        }
        return score
    }

    private fun setAnswers(state: UiState, currentAnswer: Int): List<Int> {
        return state.answers.toMutableList().apply { add(currentAnswer) }
    }
}

private operator fun <T> MutableStateFlow<T>.plusAssign(uiState: T) {
    value = uiState
}


data class UiState(
    val questions: List<Question> = listOf(),
    val currentQuestions: Question? = Question(),
    val currentIndex: Int = 0,
    val isCompleted: Boolean = false,
    val answers: List<Int> = mutableListOf(),
    val currentAnswer: Int = -1,
    val progress: Float = 0.0f,
    val score: Int = 0,
    val moveToNextCompleteScreen: Boolean = false
)