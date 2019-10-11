package com.martiniriga.moviedb.utils

import com.martiniriga.moviedb.data.gson.Movie
import com.martiniriga.moviedb.data.repos.MovieRepository
import com.martiniriga.moviedb.viewmodels.MainViewModelFactory
import com.martiniriga.moviedb.viewmodels.MovieDetailViewModelFactory

object InjectorUtils {

    fun provideMainViewModelFactory(): MainViewModelFactory {
        val repository = getMovieRepository()
        return MainViewModelFactory(repository)
    }


    fun provideMovieDetailViewModelFactory(movie: Movie): MovieDetailViewModelFactory {
        val repository = getMovieRepository()
        return MovieDetailViewModelFactory(movie, repository)
    }


    private fun getMovieRepository(): MovieRepository {
        return MovieRepository.getInstance()
    }


}