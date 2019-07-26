package com.jpp.mpdomain.repository

import com.jpp.mpdomain.*

/**
 * Repository definition to access all [MoviePage] related data.
 */
interface MoviePageRepository {

    /**
     * Retrieves a [MoviePage] for the provided [section] and [language].
     * @return The [MoviePage] indicated by [page] if any [MoviePage] exists for it.
     * Otherwise, null.
     */
    fun getMoviePageForSection(page: Int, section: MovieSection, language: SupportedLanguage): MoviePage?

    /**
     * Retrieves a [MoviePage] with the favorite movies that the user has.
     * @return a [MoviePage] indicated by [page] only if the user has movies as favorites.
     */
    fun getFavoriteMoviePage(page: Int, userAccount: UserAccount, session: Session, language: SupportedLanguage): MoviePage?

    /**
     * Retrieves a [MoviePage] with the movies that the user has rated.
     * @return a [MoviePage] indicated by [page] only if the user has movies rated.
     */
    fun getRatedMoviePage(page: Int, userAccount: UserAccount, session: Session, language: SupportedLanguage): MoviePage?

    /**
     * Retrieves a [MoviePage] from the user's watchlist.
     * @return a [MoviePage] indicated by [page] only if the user has one in the watchlist.
     */
    fun getWatchlistMoviePage(page: Int, userAccount: UserAccount, session: Session, language: SupportedLanguage): MoviePage?

    /**
     * Flushes out any favorite [MoviePage] data stored locally on the device.
     */
    fun flushFavoriteMoviePages()

    /**
     * Flushes out any rated [MoviePage] data stored locally on the device.
     */
    fun flushRatedMoviePages()

    /**
     * Flushes out any watchlist [MoviePage] data stored locally on the device.
     */
    fun flushWatchlistMoviePages()
}