package sk.sksv.newsappcompose.data.repository_impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import sk.sksv.newsappcompose.data.local.NewsDao
import sk.sksv.newsappcompose.data.remote.NewsApi
import sk.sksv.newsappcompose.data.remote.NewsPagingSource
import sk.sksv.newsappcompose.data.remote.SearchPagingNewsSource
import sk.sksv.newsappcompose.domain.model.Article
import sk.sksv.newsappcompose.domain.repository.NewsRepository
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers

class NewsRepositoryImpl(
    private val newsApi: NewsApi,
    private val newsDao: NewsDao,
    private val context: android.content.Context
) : NewsRepository {
    override fun getNews(sources: List<String>): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                NewsPagingSource(
                    newsApi = newsApi,
                    sources = sources.joinToString(separator = ",")
                )
            }
        ).flow
    }

    override fun searchedNews(
        searchQuery: String,
        sources: List<String>
    ): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                SearchPagingNewsSource(
                    searchQuery = searchQuery,
                    newsApi = newsApi,
                    sources = sources.joinToString(separator = ",")
                )
            }
        ).flow
    }

    override suspend fun upsertArticle(article: Article) {
        newsDao.upsert(article)
        try {
            withContext(Dispatchers.IO) {
                sk.sksv.newsappcompose.utils.DatabaseBackupHelper.backupDatabase(context)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun deleteArticle(article: Article) {
        newsDao.delete(article)
        try {
            withContext(Dispatchers.IO) {
                sk.sksv.newsappcompose.utils.DatabaseBackupHelper.backupDatabase(context)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun selectArticles(): Flow<List<Article>> {
        return newsDao.getArticles()
    }

    override suspend fun selectOneArticle(url: String): Article? {
        return newsDao.getArticle(url)
    }

    override suspend fun restoreBackup() {
        try {
            withContext(Dispatchers.IO) {
                sk.sksv.newsappcompose.utils.DatabaseBackupHelper.restoreDatabase(context)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}