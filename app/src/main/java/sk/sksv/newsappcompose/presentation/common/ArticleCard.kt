package sk.sksv.newsappcompose.presentation.common

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import sk.sksv.newsappcompose.R
import sk.sksv.newsappcompose.domain.model.Article
import sk.sksv.newsappcompose.domain.model.Source
import sk.sksv.newsappcompose.ui.theme.NewsAppComposeTheme
import sk.sksv.newsappcompose.utils.Dimens

@Composable
fun ArticleCard(modifier: Modifier = Modifier, article: Article, onclick: () -> Unit) {

    val context = LocalContext.current
    Row(modifier = modifier.clickable { onclick() }) {
        AsyncImage(
            modifier = modifier
                .size(Dimens.ArticleCardSize96)
                .clip(MaterialTheme.shapes.medium),
            model = ImageRequest.Builder(context).data(article.urlToImage).build(),
            contentDescription = null
        )
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(
                    horizontal = Dimens.ExtraSmallPadding3
                )
                .height(Dimens.ArticleCardSize96)
        ) {
            Text(
                text = article.title,
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(
                    R.color.text_title
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = article.source.name,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = colorResource(R.color.body)
                )
                Spacer(modifier = Modifier.width(Dimens.ExtraSmallPadding6))

                Icon(
                    painter = painterResource(id = R.drawable.ic_time), contentDescription = null,
                    modifier = Modifier.size(Dimens.SmallIconSize11),
                    tint = colorResource(id = R.color.body)
                )
                Spacer(modifier = modifier.width(Dimens.ExtraSmallPadding3))
                Text(
                    text = article.publishedAt,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = colorResource(R.color.body)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ArticleCardPreview() {
    NewsAppComposeTheme {
        ArticleCard(
            article = Article(
                author = "sandeep",
                content = "Demo data",
                description = "demo",
                publishedAt = "2 hours",
                source = Source("SK", "BBC"),
                title = "Sk sandeep Kumar",
                url = "",
                urlToImage = "https://images.moneycontrol.com/static-mcnews/2023/05/US-VISA.jpg"
            )
        ) { }
    }
}
