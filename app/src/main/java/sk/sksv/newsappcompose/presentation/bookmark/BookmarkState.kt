package sk.sksv.newsappcompose.presentation.bookmark

import sk.sksv.newsappcompose.domain.model.Article

data class BookmarkState(
    val articles: List<Article> = emptyList()
)