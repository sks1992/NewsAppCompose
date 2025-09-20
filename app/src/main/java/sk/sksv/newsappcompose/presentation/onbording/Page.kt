package sk.sksv.newsappcompose.presentation.onbording

import androidx.annotation.DrawableRes
import sk.sksv.newsappcompose.R

data class Page(
    val title:String,
    val description: String,
    @DrawableRes val image: Int
)

val pages = listOf<Page>(
    Page(
        title = "Get Dollar Info ",
        description = "Lorem ipsum is simply dummy text of the printing and typesetting industry",
        image = R.drawable.onboarding1
    ),
    Page(
        title = "Home Sweet Home",
        description = "Lorem ipsum is simply dummy text of the printing and typesetting industry",
        image = R.drawable.onboarding2
    ),
    Page(
        title = "Lets Eat Good Food",
        description = "Lorem ipsum is simply dummy text of the printing and typesetting industry",
        image = R.drawable.onboarding3
    ),
)