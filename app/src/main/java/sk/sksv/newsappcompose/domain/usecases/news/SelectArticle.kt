package sk.sksv.newsappcompose.domain.usecases.news

import sk.sksv.newsappcompose.domain.model.Article
import sk.sksv.newsappcompose.domain.repository.NewsRepository

class SelectArticle(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(url: String): Article? {
        return newsRepository.selectOneArticle(url)
    }
}