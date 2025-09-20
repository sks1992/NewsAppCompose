package sk.sksv.newsappcompose.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import sk.sksv.newsappcompose.data.remote.dto.NewsResponse
import sk.sksv.newsappcompose.utils.Constants

interface NewsApi {
    @GET("everything")
    suspend fun getNews(
        @Query("page") page: Int,
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String = Constants.APP_KEY
    ): NewsResponse
}