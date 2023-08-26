package dev.youdroid.findit.domain.local

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.youdroid.findit.R
import dev.youdroid.findit.domain.entities.Question
import javax.inject.Inject

interface LocalSource {

    suspend fun getQuestions(): List<Question>
}


class LocalSourceImplementation @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) : LocalSource {
    override suspend fun getQuestions(): List<Question> {
        context.resources.openRawResource(R.raw.android).bufferedReader().use {
            return gson.fromJson(it, object : TypeToken<List<Question>>() {}.type)
        }
    }
}