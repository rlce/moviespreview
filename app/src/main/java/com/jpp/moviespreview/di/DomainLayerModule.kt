package com.jpp.moviespreview.di

import android.content.Context
import com.jpp.moviespreview.datalayer.repository.ConfigurationRepository
import com.jpp.moviespreview.datalayer.repository.MoviesRepository
import com.jpp.moviespreview.domainlayer.ConnectivityVerifier
import com.jpp.moviespreview.domainlayer.ConnectivityVerifierImpl
import com.jpp.moviespreview.domainlayer.interactor.ConfigureApplicationInteractor
import com.jpp.moviespreview.domainlayer.interactor.ConfigureMovieImagesInteractor
import com.jpp.moviespreview.domainlayer.interactor.GetMoviePageInteractor
import com.jpp.moviespreview.domainlayer.interactor.configuration.ConfigureApplicationInteractorImpl
import com.jpp.moviespreview.domainlayer.interactor.movie.ConfigureMovieImagesInteractorImpl
import com.jpp.moviespreview.domainlayer.interactor.movie.GetMoviePageInteractorImpl
import com.jpp.moviespreview.domainlayer.interactor.movie.MovieDomainMapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Provides all dependencies for the domain layer.
 */
@Module
class DomainLayerModule {

    @Provides
    @Singleton
    fun providesConnectivityVerifier(context: Context): ConnectivityVerifier = ConnectivityVerifierImpl(context)

    @Provides
    @Singleton
    fun providesConfigureApplicationInteractor(configRepository: ConfigurationRepository, connectivityVerifier: ConnectivityVerifier)
            : ConfigureApplicationInteractor = ConfigureApplicationInteractorImpl(configRepository, connectivityVerifier)

    @Provides
    @Singleton
    fun providesGetMoviePageInteractor(moviesRepository: MoviesRepository, connectivityVerifier: ConnectivityVerifier)
            : GetMoviePageInteractor = GetMoviePageInteractorImpl(moviesRepository, MovieDomainMapper(), connectivityVerifier)

    @Provides
    @Singleton
    fun providesConfigureMovieImagesInteractor(configRepository: ConfigurationRepository)
            : ConfigureMovieImagesInteractor = ConfigureMovieImagesInteractorImpl(configRepository)

}