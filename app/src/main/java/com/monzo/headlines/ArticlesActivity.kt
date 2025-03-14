package com.monzo.headlines

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.monzo.headlines.common.AppModule
import com.monzo.headlines.common.theme.HeadlinesTheme
import com.monzo.headlines.common.theme.Typography
import com.monzo.headlines.data.model.Article
import java.time.LocalDateTime
import java.time.Month
import java.util.UUID

class ArticlesActivity : ComponentActivity() {
    private val viewModel by lazy { AppModule.articlesViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HeadlinesTheme {
                ArticlesScreen(
                    state = viewModel.state.observeAsState().value!!
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticlesScreen(state: ArticlesState) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                scrollBehavior = scrollBehavior,
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            itemsIndexed(
                items = state.articles,
                key = { _, it -> it.id }
            ) { index, article ->
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ArticleListItem(article)
                    if (index < state.articles.size - 1) {
                        Divider(modifier = Modifier.padding(start = 16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ArticleListItem(article: Article) {
    Row(
        modifier = Modifier
            .padding(16.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .size(56.dp)
                .background(MaterialTheme.colorScheme.inverseOnSurface)
                .clip(RoundedCornerShape(8.dp)),
            model = article.thumbnail,
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
            Text(
                text = article.title,
                style = Typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = article.published.toString(),
                style = Typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticlesScreenPreview() {
    HeadlinesTheme {
        ArticlesScreen(
            state = ArticlesState(
                articles = listOf(
                    randomArticle(),
                    randomArticle(),
                    randomArticle(),
                    randomArticle(),
                    randomArticle(),
                )
            )
        )
    }
}

private fun randomArticle() = Article(
    id = UUID.randomUUID().toString(),
    thumbnail = "",
    title = LoremIpsum(8).values.joinToString(" "),
    published = LocalDateTime.of(2023, Month.APRIL, 23, 0, 0),
    url = ""
)