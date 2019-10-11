package com.martiniriga.moviedb.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.martiniriga.moviedb.data.gson.Movie
import com.martiniriga.moviedb.data.repos.MovieRepository

class MovieDetailViewModelFactory(
    private val movie: Movie,
    private val movieRepository: MovieRepository
) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieDetailViewModel(movie, movieRepository) as T
    }
}