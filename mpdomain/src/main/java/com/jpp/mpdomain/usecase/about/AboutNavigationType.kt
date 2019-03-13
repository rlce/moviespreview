package com.jpp.mpdomain.usecase.about

sealed class AboutNavigationType {
    object TheMovieDbTermsOfUse : AboutNavigationType()
    object AppCodeRepo : AboutNavigationType()
    object PrivacyPolicy :  AboutNavigationType()
    object GooglePlayApp : AboutNavigationType()
    object GooglePlayWeb : AboutNavigationType()
    object ShareApp : AboutNavigationType()
}