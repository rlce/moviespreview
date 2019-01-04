package com.jpp.moviespreview.screens.main.movies

import com.jpp.mpdomain.MovieSection
import javax.inject.Inject
import com.jpp.mpdomain.Movie as DomainMovie

/***************************************************************************************************
 ********* Contains all the model classes that the movies section in the UI layer supports. ********
 ***************************************************************************************************/

/**
 * Represents a section in the movies list.
 * This can be mapped one to one with MovieSection in the domain layer.
 */
sealed class UiMovieSection {
    object Playing : UiMovieSection()
    object Popular : UiMovieSection()
    object TopRated : UiMovieSection()
    object Upcoming : UiMovieSection()
}

/**
 * Represents an item in the list of Movies shown in the initial screen of the application.
 */
data class MovieItem(
        val movieId: Double,
        val headerImageUrl: String,
        val title: String,
        val contentImageUrl: String,
        val popularity: String,
        val voteCount: String
)


class MovieItemMapper @Inject constructor() {


    fun mapDomainMovie(domainMovie: DomainMovie) = with(domainMovie) {
        MovieItem(movieId = id,
                headerImageUrl = backdrop_path ?: "emptyPath",
                title = title,
                contentImageUrl = poster_path ?: "emptyPath",
                popularity = popularity.toString(),
                voteCount = vote_count.toString()
        )
    }

    fun mapMovieSection(uiMovieSection: UiMovieSection) =
        when (uiMovieSection) {
            UiMovieSection.Playing -> MovieSection.Playing
            UiMovieSection.Popular -> MovieSection.Popular
            UiMovieSection.TopRated -> MovieSection.TopRated
            UiMovieSection.Upcoming -> MovieSection.Upcoming
        }

}