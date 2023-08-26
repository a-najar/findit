@file:OptIn(ExperimentalMaterial3Api::class)

package dev.youdroid.findit.pages

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import dev.youdroid.findit.R
import dev.youdroid.findit.ui.theme.FindItTheme

@Composable
fun LandingPage(navController: NavHostController? = null) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .scrollable(
                state = scrollState, orientation = Orientation.Vertical
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        buildAnimation()
        buildStartGameButton(navController)
        buildChangeLanguage()
    }

}

@Composable
private fun buildChangeLanguage() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    TextButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .height(60.dp),
        onClick = {
            val locale = context.resources.configuration.locale.language
            val currentLocal = if (locale != "ar") {
                "ar"
            } else {
                "en"
            }
            context.findActivity()?.runOnUiThread {
                val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(currentLocal)
                AppCompatDelegate.setApplicationLocales(appLocale)
            }

        },
    ) {
        Icon(
            Icons.Outlined.Language,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Text(
            stringResource(R.string.button_change_language),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}

@Composable
private fun buildStartGameButton(navController: NavHostController?) {
    ElevatedButton(modifier = Modifier
        .height(60.dp)
        .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
        onClick = { navController?.navigate("games") }) {
        Text(
            text = stringResource(R.string.button_start_new_game),
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.onSurface,
            ),
        )
    }
}

@Composable
private fun buildAnimation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.games))
    Spacer(modifier = Modifier.height(16.dp))
    LottieAnimation(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth(),
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )
}


@Preview(
    showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun LandingPagePreview() {
    FindItTheme {
        LandingPage()
    }
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}