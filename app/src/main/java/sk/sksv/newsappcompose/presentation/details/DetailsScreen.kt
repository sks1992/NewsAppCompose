package sk.sksv.newsappcompose.presentation.details

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.request.ImageRequest
import sk.sksv.newsappcompose.R
import sk.sksv.newsappcompose.domain.model.Article
import sk.sksv.newsappcompose.domain.model.Source
import sk.sksv.newsappcompose.presentation.details.components.DetailsTopBar
import sk.sksv.newsappcompose.ui.theme.NewsAppComposeTheme
import sk.sksv.newsappcompose.utils.Dimens

@Composable
fun DetailsScreen(
    article: Article,
    event: (DetailsEvent) -> Unit,
    navigateUp: () -> Unit,
) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        DetailsTopBar(
            onBackClick = navigateUp,
            onShareClick = {
                Intent(Intent.ACTION_SEND).also {
                    it.putExtra(Intent.EXTRA_TEXT, article.url)
                    it.type = "text/plain"

                    if (it.resolveActivity(context.packageManager) != null) {
                        context.startActivity(it)
                    }
                }
            },
            onBrowsingClick = {
                Intent(Intent.ACTION_VIEW).also {
                    it.data = article.url.toUri()
                    if (it.resolveActivity(context.packageManager) != null) {
                        context.startActivity(it)
                    }
                }
            },
            onBookmarkClick = {
                event(DetailsEvent.UpsertDeleteArticle(article))
            },
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(
                start = Dimens.MediumPadding24,
                end = Dimens.MediumPadding24,
                top = Dimens.MediumPadding24
            )
        ) {

            item {
                AsyncImage(
                    model = ImageRequest.Builder(context).data(article.urlToImage).build(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimens.Size248)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(Dimens.MediumPadding24))

                Text(
                    text = article.title,
                    style = MaterialTheme.typography.displaySmall,
                    color = colorResource(R.color.text_title)
                )
                Text(
                    text = article.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(R.color.body)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun DetailScreenPrev() {
    NewsAppComposeTheme {
        DetailsScreen(
            article = Article(
                author = "",
                content = "",
                description = "",
                publishedAt = "",
                source = Source("", ""),
                title = "",
                url = "",
                urlToImage = ""
            ),
            event = {},
            navigateUp = {}
        )
    }
}
