package edu.unibo.tracker.newsAnimation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import edu.unibo.tracker.Database.newImageList
import edu.unibo.tracker.Database.newsList

@Composable
fun SwipeCardAnimation() {
    CardStack(
        cardElevation = 4.dp,
        cardBorder = BorderStroke(2.dp, MaterialTheme.colors.surface),
        cardContent = { index ->

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(320.dp)
                    .padding(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(4.dp))
                AsyncImage(
                    modifier = Modifier.height(220.dp),
                    model = newImageList[index],
                    contentDescription = null
                )
                newsList[index].title?.let {
                    Text(
                        it,
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                newsList[index].content?.let {
                    Text(
                        it,
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        }, cardCount = newsList.size
    )


}