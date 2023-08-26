package dev.youdroid.findit.pages.games

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import dev.youdroid.findit.R
import dev.youdroid.findit.ui.theme.FindItTheme

@Composable
fun GamesPage(navController: NavHostController, viewModel: GamesViewModel = hiltViewModel()) {
    GamesScaffold(listOf("Android", "IOS", "Web")) { navController.navigate("quiz-game") }
}

@Composable
fun GamesScaffold(items: List<String> = mutableListOf(), onItemClicked: (Int) -> Unit = {}) {
    Scaffold {
        Column(
            Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                text = stringResource(R.string.text_select_game),
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            GamesList(items, onItemClicked)
        }
    }
}

@Composable
private fun GamesList(gamesList: List<String>, onItemClicked: (Int) -> Unit) {
    LazyColumn {
        items(gamesList.size) { GameItem(gamesList[it]) { onItemClicked(it) } }
    }
}

@Composable
fun GameItem(itemText: String, onItemClicked: () -> Unit) {
    Box(
        Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .border(
                shape = MaterialTheme.shapes.extraLarge,
                color = MaterialTheme.colorScheme.secondary,
                width = 1.dp
            )
            .clip(MaterialTheme.shapes.extraLarge)
            .clickable { onItemClicked() }) {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            text = itemText,
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface)
        )
    }
}


@Preview
@Composable
fun GamesPagePrev() {
    FindItTheme {
        GamesScaffold(listOf("Android", "IOS", "Web")) { }
    }
}
