package com.jpp.mpmoviedetails

/**
 * Provides navigation for the movie details module.
 */
interface MovieDetailsNavigator {

    fun navigateToUserAccount()
    fun navigateToMovieCredits(
        movieId: Double,
        movieTitle: String
    )

    fun navigateToRateMovie(
        movieId: Double,
        movieImageUrl: String,
        movieTitle: String
    )

}