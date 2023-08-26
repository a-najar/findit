package dev.youdroid.findit.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import dev.youdroid.findit.R


class CompleteViewModel : ViewModel() {

}


@Composable
fun CompletePage(navController: NavHostController? = null, score: Int? = 0) {
    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            buildTitle()
            buildAnimation()
            buildMessage(score)
            buildButton(navController)
        }
    }
}

@Composable
private fun buildButton(navController: NavHostController?) {
    ElevatedButton(modifier = Modifier
        .padding(16.dp)
        .height(60.dp)
        .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
        onClick = { navController?.navigateUp() }) {
        Text(
            text = stringResource(R.string.button_back_to_home),
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.onSurface,
            ),
        )
    }
}

@Composable
private fun buildMessage(score: Int?) {
    Text(
        text = stringResource(R.string.message_congratulations, "$score"),
        style = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
        )
    )
    Spacer(modifier = Modifier.padding(5.dp))
}

@Composable
private fun buildAnimation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.congrats))
    LottieAnimation(
        modifier = Modifier
            .height(300.dp)
            .fillMaxWidth(),
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )
}

@Composable
private fun buildTitle() {
    Text(
        text = stringResource(R.string.title_congratulations),
        style = MaterialTheme.typography.displayMedium.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
        )
    )
}


@Preview
@Composable
fun CompletePagePrev() {
    CompletePage()
}