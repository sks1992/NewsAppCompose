package sk.sksv.newsappcompose.presentation.search

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import sk.sksv.newsappcompose.domain.model.Article

data class SearchState(
    val searchQuery: String = "",
    val articles: Flow<PagingData<Article>>? = null
)