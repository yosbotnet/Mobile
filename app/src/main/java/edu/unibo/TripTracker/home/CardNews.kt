package edu.unibo.tracker.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import edu.unibo.tracker.Database.News

@Composable
fun CardNews(news: News) {

    Card(
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
        shape = MaterialTheme.shapes.small,
        elevation = 4.dp

    ) {
        CardContent(news)
    }

}

@Composable
private fun CardContent(news: News) {
    Column(
        modifier = Modifier.fillMaxWidth(0.9f).padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        news.title?.let {
            Text(
                it,
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(6.dp))
            news.content?.let { it1 ->
                Text(
                    it1,
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
        }
    }

}