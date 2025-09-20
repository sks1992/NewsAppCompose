package sk.sksv.newsappcompose.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import sk.sksv.newsappcompose.data.manager_impl.LocalUserManagerImpl
import sk.sksv.newsappcompose.domain.manager.LocalUserManager
import sk.sksv.newsappcompose.domain.usecases.AppEntryUseCases
import sk.sksv.newsappcompose.domain.usecases.ReadAppEntry
import sk.sksv.newsappcompose.domain.usecases.SaveAppEntry
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
}