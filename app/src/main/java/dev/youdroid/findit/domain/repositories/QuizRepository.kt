package dev.youdroid.findit.domain.repositories

import dev.youdroid.findit.domain.entities.Question
import dev.youdroid.findit.domain.local.LocalSource
import javax.inject.Inject

interface QuizRepository {
    suspend fun getQuestions(): List<Question>
}


class QuizRepositoryImplementation @Inject constructor(
    private val localSource: LocalSource
) :
    QuizRepository {

    override suspend fun getQuestions(): List<Question> {
        return localSource.getQuestions()
    }

}