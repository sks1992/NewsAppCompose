package sk.sksv.newsappcompose.utils

object Constants {

    const val USER_SETTINGS = "userSettings"
    const val USER_SETTINGS_SECURE = "userSettingsSecure"
    const val APP_ENTRY = "appEntry"
    const val APP_KEY = "e004331040d8464892c01b7f4b70641f"
    const val BASE_URL = "https://newsapi.org/v2/"
    const val NEWS_DATABASE_NAME = "news_db"
    
    /**
     * Database password for SQLCipher encryption.
     * Same password for all devices - allows database portability.
     * 
     * ⚠️ SECURITY NOTE: This is a shared password. For better security,
     * consider using a more complex password or deriving it dynamically.
     */
    const val DATABASE_PASSWORD = "12345"
}