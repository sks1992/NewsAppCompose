package sk.sksv.newsappcompose.data.remote.dto


import com.google.gson.annotations.SerializedName
import sk.sksv.newsappcompose.domain.model.Article

data class NewsResponse(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)