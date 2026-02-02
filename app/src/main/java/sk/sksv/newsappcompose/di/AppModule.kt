package sk.sksv.newsappcompose.di

import android.app.Application
import android.util.Log
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.zetetic.database.sqlcipher.SupportOpenHelperFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sk.sksv.newsappcompose.data.local.NewsDao
import sk.sksv.newsappcompose.data.local.NewsDatabase
import sk.sksv.newsappcompose.data.local.NewsTypeConvertor
import sk.sksv.newsappcompose.data.manager_impl.LocalUserManagerImpl
import sk.sksv.newsappcompose.data.remote.NewsApi
import sk.sksv.newsappcompose.data.repository_impl.NewsRepositoryImpl
import sk.sksv.newsappcompose.domain.manager.LocalUserManager
import sk.sksv.newsappcompose.domain.repository.NewsRepository
import sk.sksv.newsappcompose.domain.usecases.app_entry.AppEntryUseCases
import sk.sksv.newsappcompose.domain.usecases.app_entry.ReadAppEntry
import sk.sksv.newsappcompose.domain.usecases.app_entry.SaveAppEntry
import sk.sksv.newsappcompose.domain.usecases.news.DeleteArticle
import sk.sksv.newsappcompose.domain.usecases.news.GetNews
import sk.sksv.newsappcompose.domain.usecases.news.NewsUseCases
import sk.sksv.newsappcompose.domain.usecases.news.SearchNews
import sk.sksv.newsappcompose.domain.usecases.news.SelectArticle
import sk.sksv.newsappcompose.domain.usecases.news.SelectArticles
import sk.sksv.newsappcompose.domain.usecases.news.UpsertArticle
import sk.sksv.newsappcompose.utils.Constants
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideLocalUserManager(application: Application): LocalUserManager =
        LocalUserManagerImpl(application)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManager: LocalUserManager
    ) = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager)
    )

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            ).build().create<NewsApi>(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsApi: NewsApi,
        newsDao: NewsDao,
        application: Application
    ): NewsRepository =
        NewsRepositoryImpl(newsApi, newsDao, application)

    @Provides
    @Singleton
    fun provideNewsDatabase(application: Application): NewsDatabase {
        // Use constant password - same for all devices
        // This allows copying database files between devices
        val passphrase = Constants.DATABASE_PASSWORD.toByteArray(Charsets.UTF_8)
        val factory = SupportOpenHelperFactory(passphrase)
        return Room.databaseBuilder(
            context = application,
            klass = NewsDatabase::class.java,
            name = Constants.NEWS_DATABASE_NAME
        ).openHelperFactory(factory)
            .addTypeConverter(NewsTypeConvertor())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(newsDatabase: NewsDatabase) = newsDatabase.newsDao


    @Provides
    @Singleton
    fun provideNewsUseCases(newsRepository: NewsRepository) = NewsUseCases(
        getNews = GetNews(newsRepository),
        searchNews = SearchNews(newsRepository),
        upsertArticle = UpsertArticle(newsRepository),
        deleteArticle = DeleteArticle(newsRepository),
        selectArticles = SelectArticles(newsRepository),
        selectArticle = SelectArticle(newsRepository),
        restoreBackup = sk.sksv.newsappcompose.domain.usecases.news.RestoreBackup(newsRepository)
    )
}