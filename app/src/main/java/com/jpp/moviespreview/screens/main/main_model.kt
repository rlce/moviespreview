package com.jpp.moviespreview.screens.main

/**
 * Represents the view state that the MainActivity can show at any given time.
 */
sealed class MainActivityViewState(val sectionTitle: String, val menuBarEnabled: Boolean) {
    data class ActionBarLocked(val abTitle: String, val withAnimation: Boolean, val menuEnabled: Boolean) : MainActivityViewState(abTitle, menuEnabled)
    data class ActionBarUnlocked(val abTitle: String, val contentImageUrl: String) : MainActivityViewState(abTitle, false)
    data class SearchEnabled(val withAnimation: Boolean) : MainActivityViewState("", false)
}


sealed class SearchEvent {
    object ClearSearch : SearchEvent()
    data class Search(val query: String) : SearchEvent()
}