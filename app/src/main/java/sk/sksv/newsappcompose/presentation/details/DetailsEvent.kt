package sk.sksv.newsappcompose.presentation.details

import sk.sksv.newsappcompose.domain.model.Article

sealed class DetailsEvent {
    data class UpsertDeleteArticle(val article: Article): DetailsEvent()

    object RemoveSideEffect : DetailsEvent()
}