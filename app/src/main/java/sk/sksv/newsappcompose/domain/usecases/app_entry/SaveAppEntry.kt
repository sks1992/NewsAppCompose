package sk.sksv.newsappcompose.domain.usecases.app_entry

import sk.sksv.newsappcompose.domain.manager.LocalUserManager

class SaveAppEntry(private val localUserManager: LocalUserManager) {

    //we use operator so we can access this function with class name.
    suspend operator fun invoke() {
        localUserManager.saveAppEntry()
    }
}