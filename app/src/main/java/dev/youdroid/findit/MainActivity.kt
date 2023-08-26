@file:OptIn(ExperimentalMaterial3Api::class)

package dev.youdroid.findit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.PieChart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import dev.youdroid.findit.pages.CompletePage
import dev.youdroid.findit.pages.LandingPage
import dev.youdroid.findit.pages.ProfilePage
import dev.youdroid.findit.pages.ProgressPage
import dev.youdroid.findit.pages.games.GamesPage
import dev.youdroid.findit.pages.quiz.QuizGamePage
import dev.youdroid.findit.ui.theme.FindItTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FindItTheme { App() }
        }
    }
}


@Composable
fun App() {
    val navController = rememberNavController()
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(topBar = {
            CenterAlignedTopAppBar(navigationIcon = {
                IconButton(onClick = { navController.navigate("profile") }) {
                    Icon(Icons.Outlined.PersonOutline, contentDescription = null)
                }

            }, title = { Text("Find It") }, actions = {
                IconButton(onClick = { navController.navigate("progress") }) {
                    Icon(Icons.Outlined.PieChart, contentDescription = null)
                }
            })
        }) {
            NavHost(
                modifier = Modifier.padding(it),
                navController = navController,
                startDestination = "landing"
            ) {
                composable("landing") { LandingPage(navController) }
                composable("progress") { ProgressPage() }
                composable("quiz-game") { QuizGamePage(navController) }
                composable("profile") { ProfilePage() }
                composable("complete?score={score}", arguments = listOf(navArgument("score") {
                    defaultValue = 0
                    type = NavType.IntType
                })) { nav ->
                    val score = nav.arguments?.getInt("score")
                    CompletePage(navController, score)
                }
                composable("games") { GamesPage(navController) }
            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FindItTheme {
        App()
    }
}