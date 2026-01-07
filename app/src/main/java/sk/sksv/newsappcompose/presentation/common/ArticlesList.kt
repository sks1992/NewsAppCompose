package sk.sksv.newsappcompose.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import sk.sksv.newsappcompose.domain.model.Article
import sk.sksv.newsappcompose.utils.Dimens

@Composable
fun ArticlesList(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
    onClick: (Article) -> Unit
) {
    val handlePagingResult = handlePagingResult(articles = articles)
    if (handlePagingResult) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(Dimens.MediumPadding24),
            contentPadding = PaddingValues(all = Dimens.ExtraSmallPadding6)
        ) {

            items(count = articles.itemCount) {
                articles[it]?.let {
                    ArticleCard(article = it, onclick = { onClick(it) }, modifier = modifier)
                }
            }
        }
    }
}

@Composable
fun ArticlesList(
    modifier: Modifier = Modifier,
    articles: List<Article>,
    onClick: (Article) -> Unit
) {
    if (articles.isEmpty()) EmptyScreen()
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(Dimens.MediumPadding24),
        contentPadding = PaddingValues(all = Dimens.ExtraSmallPadding6)
    ) {

        items(count = articles.size) {
            val article = articles[it]
            ArticleCard(article = article, onclick = { onClick(article) }, modifier = modifier)
        }
    }
}

@Composable
fun handlePagingResult(
    articles: LazyPagingItems<Article>,
): Boolean {
    val loadState = articles.loadState
    val error = when {
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        else -> null
    }

    return when {
        loadState.refresh is LoadState.Loading -> {
            ShimmerEffect()
            false
        }

        error != null -> {
            EmptyScreen()
            false
        }

        else -> {
            true
        }
    }
}

@Composable
private fun ShimmerEffect() {
    Column(verticalArrangement = Arrangement.spacedBy(Dimens.MediumPadding24)) {
        repeat(10) {
            ArticleCardShimmerEffect(
                modifier = Modifier.padding(horizontal = Dimens.MediumPadding24)
            )
        }
    }
}