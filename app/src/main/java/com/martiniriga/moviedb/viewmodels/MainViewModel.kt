package com.martiniriga.moviedb.viewmodels

import androidx.lifecycle.ViewModel
import com.martiniriga.moviedb.data.repos.MovieRepository


class MainViewModel internal constructor(private val moviesRepository: MovieRepository) : ViewModel() {
    private lateinit var token:String


    fun getConfig() = moviesRepository.getConfig()

    fun fetchConfig() = moviesRepository.fetchConfiguration()

    fun getData() = moviesRepository.getData()

    fun fetchMovies(sortBy:String) = moviesRepository.fetchMovies(sortBy)
    
    fun getErrors() = moviesRepository.getErrors()

    fun getLoader() = moviesRepository.getLoader()
}