package sk.sksv.newsappcompose.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import sk.sksv.newsappcompose.domain.model.Article

interface NewsRepository {
    fun getNews(sources: List<String>): Flow<PagingData<Article>>
    fun searchedNews(searchQuery: String, sources: List<String>): Flow<PagingData<Article>>

    suspend fun upsertArticle(article: Article)

    suspend fun deleteArticle(article: Article)

    fun selectArticles(): Flow<List<Article>>

    suspend fun selectOneArticle(url: String): Article?

}