package dev.youdroid.findit.domain.usecases

import dev.youdroid.findit.domain.entities.Question
import dev.youdroid.findit.domain.repositories.QuizRepository
import javax.inject.Inject

class GetQuizUseCase @Inject constructor(private val quizRepository: QuizRepository) :
    suspend () -> List<Question> {
    override suspend fun invoke(): List<Question> {
        return quizRepository.getQuestions()
    }
}