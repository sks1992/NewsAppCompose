package sk.sksv.newsappcompose.domain.usecases.news

import kotlinx.coroutines.flow.Flow
import sk.sksv.newsappcompose.domain.model.Article
import sk.sksv.newsappcompose.domain.repository.NewsRepository

class SelectArticles(
    private val newsRepository: NewsRepository
) {

    operator fun invoke(): Flow<List<Article>> {
        return newsRepository.selectArticles()
    }
}