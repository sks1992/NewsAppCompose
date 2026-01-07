package sk.sksv.newsappcompose.presentation.bookmark

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import sk.sksv.newsappcompose.R
import sk.sksv.newsappcompose.domain.model.Article
import sk.sksv.newsappcompose.presentation.common.ArticlesList
import sk.sksv.newsappcompose.utils.Dimens


@Composable
fun BookmarkScreen(
    state: BookmarkState,
    navigateToDetails: (Article) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(
                top = Dimens.MediumPadding24,
                start = Dimens.MediumPadding24,
                end = Dimens.MediumPadding24
            )
    ) {

        Text(
            text = "Bookmark",
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
            color = colorResource(
                id = R.color.text_title
            )
        )

        Spacer(modifier = Modifier.height(Dimens.MediumPadding24))

        ArticlesList(
            articles = state.articles,
            onClick = {
                navigateToDetails(it)
            }
        )
    }

}