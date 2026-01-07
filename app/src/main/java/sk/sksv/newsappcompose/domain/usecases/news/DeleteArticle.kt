package sk.sksv.newsappcompose.domain.usecases.news

import sk.sksv.newsappcompose.domain.model.Article
import sk.sksv.newsappcompose.domain.repository.NewsRepository
import javax.inject.Inject

class DeleteArticle @Inject constructor(
    private val newsRepository: NewsRepository
) {

    suspend operator fun invoke(article: Article) {
        newsRepository.deleteArticle(article = article)
    }

}