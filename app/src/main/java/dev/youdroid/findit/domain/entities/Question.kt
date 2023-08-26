package dev.youdroid.findit.domain.entities

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class Question(
    @SerializedName("correct_answer_index")
    val correctAnswerIndex: Int = 0,
    @SerializedName("options")
    val options: List<Map<String, String>> = listOf(),
    @SerializedName("question")
    val question: Map<String, String> = mapOf()
) : Parcelable