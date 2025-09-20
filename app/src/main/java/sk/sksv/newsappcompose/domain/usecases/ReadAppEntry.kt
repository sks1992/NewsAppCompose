package sk.sksv.newsappcompose.domain.usecases

import kotlinx.coroutines.flow.Flow
import sk.sksv.newsappcompose.domain.manager.LocalUserManager

class ReadAppEntry(
    private val localUserManager: LocalUserManager
) {
    operator fun invoke(): Flow<Boolean> {
        return localUserManager.readAppEntry()
    }
}