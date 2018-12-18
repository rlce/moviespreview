package com.jpp.moviespreview.screens.main.movies.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.jpp.moviespreview.domainlayer.Movie
import com.jpp.moviespreview.domainlayer.interactor.ConfigureMovieImagesInteractor
import com.jpp.moviespreview.domainlayer.interactor.GetMoviePageInteractor
import com.jpp.moviespreview.screens.main.movies.UiMovieSection
import javax.inject.Inject


/**
 * Factory class to create the DataSource that will provide the pages to show in the movies section
 * of the application.
 * An instance of this class is injected into the ViewModels to hook the callbacks to the UI.
 */
class MoviesPagingDataSourceFactory @Inject constructor(private val moviePageInteractor: GetMoviePageInteractor,
                                                        private val configureMovieImagesInteractor: ConfigureMovieImagesInteractor) : DataSource.Factory<Int, Movie>() {


    val dataSourceLiveData by lazy { MutableLiveData<MoviesPagingDataSource>() }
    var config: MoviesPagingConfig? = null


    override fun create(): DataSource<Int, Movie> {
        config?.let {
            val dataSource = MoviesPagingDataSource(moviePageInteractor, configureMovieImagesInteractor, it.section, it.moviePosterSize, it.movieBackdropSize)
            dataSourceLiveData.postValue(dataSource)
            return dataSource
        }
        throw IllegalStateException("You need to provide a section to initialize the data source")
    }


    data class MoviesPagingConfig(
            val section: UiMovieSection,
            val moviePosterSize: Int,
            val movieBackdropSize: Int
    )
}