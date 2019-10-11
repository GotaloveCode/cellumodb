package com.martiniriga.moviedb.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.martiniriga.moviedb.data.gson.Movie
import com.martiniriga.moviedb.data.repos.MovieRepository
import com.martiniriga.moviedb.utils.Helpers
import java.text.SimpleDateFormat
import java.util.*


class MovieDetailViewModel internal constructor(
    private val movie: Movie,
    private val movieRepository: MovieRepository
) : ViewModel() {


    private val _name = MutableLiveData<String>().apply { value = movie.title }
    val name: LiveData<String> = _name

    private val _image = MutableLiveData<String>().apply { value = movie.poster_path }
    val imageUrl: LiveData<String> = _image

    private val _content = MutableLiveData<String>().apply { value = movie.overview }
    val content: LiveData<String> = _content

    private val _date = MutableLiveData<String>().apply { value =  Helpers.formatLongEndDay(movie.release_date) }
    val date: LiveData<String> = _date


    private val _mapclicks = MutableLiveData<Int>().apply { value = 0 }
    val mapclicks: LiveData<Int> = _mapclicks


//
//    fun onFavoriteClick(){
//        _isfavorite.value = !isfavorite.value!!
//        favorite()
//    }

//    private fun onRsvpClick(rsv: String){
//        _rsvpClick.value = _rsvpClick.value!! + 1
//        _hasRsvp.value = true
//        _rsvString.value = reserveNotification(rsv)
//        _eventIcon.value = reserveDrawables(rsv)
//        _rsvp.value = rsv
//        movieRepository.rsvp(movie.id,rsv,token)
//    }

    fun getErrors() = movieRepository.getErrors()

//    private fun favorite() = movieRepository.favorite(movie.id,token)

    fun getLoader() = movieRepository.getLoader()

    fun mapClick(){
        _mapclicks.value = _mapclicks.value!! + 1
    }


}