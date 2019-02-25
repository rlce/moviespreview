package com.jpp.moviespreview.screens.main.movies

import androidx.paging.PagedList

/**
 * Represents the view state of the movies view (MoviesFragment).
 * Each subclass of this sealed class represents a particular state that the fragment
 * can assume.
 */
sealed class MoviesViewState {
    object Loading : MoviesViewState()
    object ErrorNoConnectivity : MoviesViewState()
    object ErrorNoConnectivityWithItems : MoviesViewState()
    object ErrorUnknown : MoviesViewState()
    object ErrorUnknownWithItems : MoviesViewState()
    data class InitialPageLoaded(val pagedList: PagedList<MovieItem>) : MoviesViewState()
}

/**
 * Represents the navigation events that can be routed through the search section.
 */
sealed class MoviesViewNavigationEvent {
    data class ToMovieDetails(val movieId: String, val movieImageUrl: String, val movieTitle: String) : MoviesViewNavigationEvent()
}