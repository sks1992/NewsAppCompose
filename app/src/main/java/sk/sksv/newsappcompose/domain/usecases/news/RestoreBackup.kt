package sk.sksv.newsappcompose.domain.usecases.news

import sk.sksv.newsappcompose.domain.repository.NewsRepository

class RestoreBackup(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke() {
        newsRepository.restoreBackup()
    }
}
