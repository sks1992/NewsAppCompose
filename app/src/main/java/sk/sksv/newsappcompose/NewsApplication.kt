package sk.sksv.newsappcompose

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import net.zetetic.database.sqlcipher.SQLiteDatabase

@HiltAndroidApp
class NewsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        System.loadLibrary("sqlcipher")
    }
}