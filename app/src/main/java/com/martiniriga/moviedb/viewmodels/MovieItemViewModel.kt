package com.martiniriga.moviedb.viewmodels

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.martiniriga.moviedb.data.gson.Movie
import com.martiniriga.moviedb.utils.Helpers

class MovieItemViewModel(
    movie: Movie
) : ViewModel() {

    val title = ObservableField<String>(movie.title)
    val imageUrl = ObservableField<String>(movie.poster_path)
    val rating = ObservableField<Int>(movie.vote_average.toInt())
    val releaseDate = ObservableField<String>(movie.release_date)
//    (Helpers.formatLongEndDay(movie.release_date))
    val count = ObservableField<String>(movie.vote_average.toString())
}