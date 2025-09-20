package sk.sksv.newsappcompose.presentation.onbording.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import sk.sksv.newsappcompose.R
import sk.sksv.newsappcompose.presentation.onbording.Page
import sk.sksv.newsappcompose.presentation.onbording.pages
import sk.sksv.newsappcompose.ui.theme.NewsAppComposeTheme
import sk.sksv.newsappcompose.utils.Dimens

@Composable
fun OnBoardingPage(modifier: Modifier = Modifier, page: Page) {

    Column(modifier = modifier) {
        Image(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.6f),
            painter = painterResource(id = page.image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = modifier.height(height = Dimens.MediumPadding24))
        Text(
            text = page.title,
            modifier = modifier.padding(horizontal = Dimens.MediumPadding30),
            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
            color = colorResource(id = R.color.display_small)
        )
        Spacer(modifier = modifier.height(height = Dimens.MediumPadding24))
        Text(
            text = page.description,
            modifier = modifier.padding(horizontal = Dimens.MediumPadding30),
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(id = R.color.text_medium)
        )
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun OnBoardingPreview() {
    NewsAppComposeTheme {
        OnBoardingPage(page = pages[0])
    }
}