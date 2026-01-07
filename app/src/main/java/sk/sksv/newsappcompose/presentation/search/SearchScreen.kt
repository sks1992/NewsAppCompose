package sk.sksv.newsappcompose.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import sk.sksv.newsappcompose.domain.model.Article
import sk.sksv.newsappcompose.presentation.common.ArticlesList
import sk.sksv.newsappcompose.presentation.common.SearchBar
import sk.sksv.newsappcompose.utils.Dimens

@Composable
fun SearchScreen(
    state: SearchState,
    event: (SearchEvent) -> Unit,
    navigateToDetails: (Article) -> Unit
) {

    Column(
        modifier = Modifier
            .padding(
                start = Dimens.MediumPadding24,
                end = Dimens.MediumPadding24,
                top = Dimens.MediumPadding24
            )
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        SearchBar(
            text = state.searchQuery, readOnly = false, onValueChange = {
                event(SearchEvent.UpdateSearchQuery(it))
            },
            onSearch = {
                event(SearchEvent.SearchNews)
            }
        )

        Spacer(modifier = Modifier.height(Dimens.MediumPadding24))

        state.articles?.let {
            val articles = it.collectAsLazyPagingItems()
            ArticlesList(
                articles = articles,
                onClick = { navigateToDetails(it) }
            )
        }
    }

}