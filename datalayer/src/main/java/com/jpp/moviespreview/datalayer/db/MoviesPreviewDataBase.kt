package com.jpp.moviespreview.datalayer.db

import com.jpp.moviespreview.datalayer.AppConfiguration
import com.jpp.moviespreview.datalayer.MoviePage

interface MoviesPreviewDataBase {
    fun getStoredAppConfiguration() : AppConfiguration?
    fun updateAppConfiguration(appConfiguration: AppConfiguration)
    fun isCurrentMovieTypeStored(movieType: MovieType): Boolean
    fun updateCurrentMovieTypeStored(movieType: MovieType)
    fun getMoviePage(page: Int): MoviePage?
    fun updateMoviePage(page: MoviePage)
    fun clearMoviePagesStored()
}
