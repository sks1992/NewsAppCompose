package sk.sksv.newsappcompose.domain.usecases.news

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import sk.sksv.newsappcompose.domain.model.Article
import sk.sksv.newsappcompose.domain.repository.NewsRepository

class SearchNews(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(searchQuery: String, sources: List<String>): Flow<PagingData<Article>> =
        newsRepository.searchedNews(searchQuery = searchQuery, sources = sources)

}