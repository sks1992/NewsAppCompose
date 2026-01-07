package sk.sksv.newsappcompose.presentation.news_navigator.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sk.sksv.newsappcompose.R
import sk.sksv.newsappcompose.ui.theme.NewsAppComposeTheme
import sk.sksv.newsappcompose.utils.Dimens

@Composable
fun NewsBottomNavigation(
    items: List<BottomNavigationItems>,
    selectedItem: Int,
    onItemClick: (Int) -> Unit
) {

    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 10.dp
    ) {
        items.forEachIndexed { index, items ->
            NavigationBarItem(
                selected = index == selectedItem,
                onClick = { onItemClick(index) },
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = items.icon),
                            contentDescription = items.text,
                            modifier = Modifier.size(Dimens.IconSize)
                        )
                        Spacer(modifier = Modifier.height(Dimens.ExtraSmallPadding6))
                        Text(
                            text = items.text,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = colorResource(id = R.color.body),
                    unselectedTextColor = colorResource(id = R.color.body),
                    indicatorColor = MaterialTheme.colorScheme.background
                )
            )
        }

    }
}

data class BottomNavigationItems(
    @DrawableRes val icon: Int,
    val text: String
)


@Preview
@Composable
private fun NewsBottomNavigationPrev() {
    NewsAppComposeTheme {
        NewsBottomNavigation(
            items = listOf(
                BottomNavigationItems(icon = R.drawable.ic_home, text = "Home"),
                BottomNavigationItems(icon = R.drawable.ic_search, text = "Search"),
                BottomNavigationItems(icon = R.drawable.ic_bookmark, text = "Bookmark"),
            ), selectedItem = 0, onItemClick = {})
    }
}