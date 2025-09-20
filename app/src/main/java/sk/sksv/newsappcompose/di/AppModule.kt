package sk.sksv.newsappcompose.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sk.sksv.newsappcompose.data.manager_impl.LocalUserManagerImpl
import sk.sksv.newsappcompose.data.remote.NewsApi
import sk.sksv.newsappcompose.data.repository_impl.NewsRepositoryImpl
import sk.sksv.newsappcompose.domain.manager.LocalUserManager
import sk.sksv.newsappcompose.domain.repository.NewsRepository
import sk.sksv.newsappcompose.domain.usecases.app_entry.AppEntryUseCases
import sk.sksv.newsappcompose.domain.usecases.app_entry.ReadAppEntry
import sk.sksv.newsappcompose.domain.usecases.app_entry.SaveAppEntry
import sk.sksv.newsappcompose.domain.usecases.news.GetNews
import sk.sksv.newsappcompose.domain.usecases.news.NewsUseCases
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
    fun provideNewsRepository(newsApi: NewsApi): NewsRepository = NewsRepositoryImpl(newsApi)

    @Provides
    @Singleton
    fun provideNewsUseCases(newsRepository: NewsRepository) = NewsUseCases(
        getNews = GetNews(newsRepository)
    )
}