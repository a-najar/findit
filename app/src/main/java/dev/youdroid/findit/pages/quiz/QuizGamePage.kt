package dev.youdroid.findit.pages.quiz

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import dev.youdroid.findit.R
import dev.youdroid.findit.domain.entities.Question
import dev.youdroid.findit.ui.theme.FindItTheme
import java.util.Locale

@Composable
fun QuizGamePage(
    navController: NavHostController? = null, viewModel: QuizGameViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    QuizGameScreen(
        uiState = uiState,
        onCompleted = viewModel::submitCompleted,
        submitNext = viewModel::submitAndNext
    )

    SideEffect {
        if (uiState.moveToNextCompleteScreen) {
            navController?.navigate("complete?score=${uiState.score}") {
                popUpTo("landing")
            }
        }
    }
}


@Composable
fun QuizGameScreen(
    uiState: UiState,
    onCompleted: (selection: Int, uiState: UiState) -> Unit,
    submitNext: (selection: Int, uiState: UiState) -> Unit,
) {
    var currentSelectedAnswer: Int by rememberSaveable { mutableIntStateOf(uiState.currentAnswer) }
    Scaffold {
        Box(
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column {
                ProgressStatus(uiState)
                QuestionText(uiState)
                AnswersList(
                    uiState.currentQuestions?.options?.map { item -> item[Locale.getDefault().language].orEmpty() }
                        .orEmpty(),
                    currentSelectedAnswer,
                ) { index -> currentSelectedAnswer = index }
                Spacer(modifier = Modifier.height(16.dp))
            }
            NextButton(
                Modifier.align(Alignment.BottomCenter),
                isButtonEnabled = currentSelectedAnswer != -1,
                currentText = if (uiState.isCompleted) stringResource(R.string.button_complete)
                else stringResource(R.string.button_next_answer)
            ) {
                if (!uiState.isCompleted) {
                    submitNext(currentSelectedAnswer, uiState)
                } else {

                    onCompleted(currentSelectedAnswer, uiState)
                }

                currentSelectedAnswer = -1
            }
        }
    }
}


@Composable
private fun NextButton(
    modifier: Modifier = Modifier,
    isButtonEnabled: Boolean,
    currentText: String,
    onClick: () -> Unit
) {
    ElevatedButton(
        modifier = modifier
            .padding(16.dp)
            .height(60.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary,
            disabledContentColor = MaterialTheme.colorScheme.primary
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
        enabled = isButtonEnabled,
        onClick = onClick
    ) {
        Text(
            text = currentText, style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.onSurface,
            )
        )
    }
}

@Composable
private fun QuestionText(uiState: UiState) {
    AnimatedContent(
        targetState = uiState.currentQuestions?.question?.get(Locale.getDefault().language)
            .orEmpty(), label = "Question"
    ) { content ->
        Text(
            modifier = Modifier.padding(16.dp),
            text = content,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun ProgressStatus(
    uiState: UiState
) {
    val progressAnimation by animateFloatAsState(
        targetValue = uiState.progress,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
        label = ""
    )

    Column(modifier = Modifier.padding(16.dp)) {
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp),
            progress = progressAnimation,
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.inversePrimary,
            strokeCap = StrokeCap.Round
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = stringResource(
                R.string.text_progress_completed,
                uiState.currentIndex.plus(1),
                uiState.questions.size
            ), style = MaterialTheme.typography.labelSmall.copy(
                color = MaterialTheme.colorScheme.onSurface,
            )
        )
    }
}

@Composable
private fun AnswersList(
    items: List<String>, selected: Int, onAnswerSelected: (Int) -> Unit
) {
    AnimatedContent(targetState = items, label = "items") { animatedItems ->
        LazyColumn {
            items(animatedItems.size) { index ->
                AnswerItem(items[index], selected == index) { onAnswerSelected(index) }
            }
        }
    }
}


@Composable
fun AnswerItem(
    text: String, selected: Boolean,
    onOptionSelected: () -> Unit,
) {

    val bgColor by animateColorAsState(
        if (selected) {
            MaterialTheme.colorScheme.secondary
        } else {
            Color.Transparent
        }, label = "bgColor"
    )

    val borderColor by animateColorAsState(
        if (selected) {
            MaterialTheme.colorScheme.secondary
        } else {
            MaterialTheme.colorScheme.primary
        }, label = "borderColor"
    )


    val textColor by animateColorAsState(
        if (selected) MaterialTheme.colorScheme.surface
        else MaterialTheme.colorScheme.onSurface, label = "borderColor"
    )
    Surface(
        shape = MaterialTheme.shapes.extraLarge,
        color = bgColor,
        border = BorderStroke(
            width = 1.dp, color = borderColor
        ),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(MaterialTheme.shapes.extraLarge)
            .selectable(
                selected, onClick = onOptionSelected, role = Role.RadioButton
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold, color = textColor
                )
            )
            RadioButton(
                selected = selected, onClick = null, colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colorScheme.surface,
                    unselectedColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}

@Preview(
    showBackground = true, uiMode = UI_MODE_NIGHT_NO
)
@Composable
fun QuizGamePagePrev() {
    FindItTheme {
        val current = Question(
            question = mapOf("en" to "En"), options = listOf(
                mapOf("en" to "En"), mapOf("en" to "En"), mapOf("en" to "En"), mapOf("en" to "En")
            )
        )
        val uiState = UiState(
            questions = listOf(current, current, current, current, current),
            currentQuestions = current,
            currentIndex = 0,
            isCompleted = true,
            progress = 0.1f,
            currentAnswer = 0
        )
        QuizGameScreen(uiState, { int, state -> }) { int, state -> }
    }
}