package sk.sksv.newsappcompose.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import sk.sksv.newsappcompose.R
import sk.sksv.newsappcompose.domain.model.Article
import sk.sksv.newsappcompose.presentation.common.ArticlesList
import sk.sksv.newsappcompose.presentation.common.SearchBar
import sk.sksv.newsappcompose.utils.Dimens

@Composable
fun HomeScreen(
    articles: LazyPagingItems<Article>,
    navigateToSearch: () -> Unit,
    navigateToDetail: (Article) -> Unit,
) {
    val titles by remember {
        derivedStateOf {
            if (articles.itemCount > 10) {
                articles.itemSnapshotList
                    .items
                    .slice(IntRange(start = 0, endInclusive = 9))
                    .joinToString(separator = " \uD83d\uDFE5 ") {
                        it.title
                    }
            } else {
                ""
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = Dimens.MediumPadding24)
            .statusBarsPadding()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier
                .width(150.dp)
                .height(30.dp)
                .padding(horizontal = Dimens.MediumPadding24)
        )
        Spacer(modifier = Modifier.height(Dimens.MediumPadding24))

        SearchBar(
            modifier = Modifier
                .padding(horizontal = Dimens.MediumPadding24),
            text = "", onValueChange = {}, readOnly = true, onClick = {
                navigateToSearch()
            }, onSearch = {})

        Spacer(modifier = Modifier.height(Dimens.MediumPadding24))

        Text(
            text = titles,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = Dimens.MediumPadding24)
                .basicMarquee(),
            fontSize = 12.sp,
            color = colorResource(id = R.color.placeholder)
        )

        Spacer(modifier = Modifier.height(Dimens.MediumPadding24))

        ArticlesList(
            modifier = Modifier.padding(horizontal = Dimens.ExtraSmallPadding6),
            articles = articles,
            onClick = {
                navigateToDetail(it)
            })
    }
}