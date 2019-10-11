package com.martiniriga.moviedb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.martiniriga.moviedb.data.gson.Movie
import com.martiniriga.moviedb.databinding.ActivityMovieDetailBinding
import com.martiniriga.moviedb.utils.InjectorUtils
import com.martiniriga.moviedb.viewmodels.MovieDetailViewModel
import android.content.Intent
import android.content.SharedPreferences

import android.net.Uri
import com.martiniriga.moviedb.utils.Constants
import com.martiniriga.moviedb.utils.PreferenceHelper
import com.martiniriga.moviedb.utils.PreferenceHelper.get



class MovieDetailActivity : AppCompatActivity() {
    private lateinit var prefs: SharedPreferences
    private lateinit var movieViewModel: MovieDetailViewModel
    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = PreferenceHelper.defaultPrefs(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)

        initViewModel()

        binding.btnViewMovie.setOnClickListener {
//           Note: Couldnt find any video url from api but concept remains the same
            val baseurl:String? = prefs[Constants.imageUrl]
            val movieurl =  baseurl + movie.poster_path
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(movieurl)
            startActivity(i)
        }

    }

    private fun initViewModel() {
        movie = intent.getParcelableExtra("movie")
        val factory = InjectorUtils.provideMovieDetailViewModelFactory(movie)
        movieViewModel = ViewModelProviders.of(this, factory).get(MovieDetailViewModel::class.java)
        binding.apply {
            viewModel = movieViewModel
            lifecycleOwner = this@MovieDetailActivity
        }

    }
}
