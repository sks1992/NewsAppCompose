package sk.sksv.newsappcompose.domain.usecases.news

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import sk.sksv.newsappcompose.domain.model.Article
import sk.sksv.newsappcompose.domain.repository.NewsRepository

class GetNews(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(source: List<String>): Flow<PagingData<Article>> {
        return newsRepository.getNews(source)
    }
}