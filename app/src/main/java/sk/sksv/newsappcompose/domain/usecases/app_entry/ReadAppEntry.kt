package sk.sksv.newsappcompose.domain.usecases.app_entry

import kotlinx.coroutines.flow.Flow
import sk.sksv.newsappcompose.domain.manager.LocalUserManager

class ReadAppEntry(
    private val localUserManager: LocalUserManager
) {
    operator fun invoke(): Flow<Boolean> {
        return localUserManager.readAppEntry()
    }
}