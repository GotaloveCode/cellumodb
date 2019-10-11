package com.martiniriga.moviedb

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.martiniriga.moviedb.adapters.MovieAdapter
import com.martiniriga.moviedb.data.gson.Movie
import com.martiniriga.moviedb.databinding.ActivityMainBinding
import com.martiniriga.moviedb.utils.*
import com.martiniriga.moviedb.utils.PreferenceHelper.get
import com.martiniriga.moviedb.utils.PreferenceHelper.set
import com.martiniriga.moviedb.viewmodels.MainViewModel
import com.martiniriga.moviedb.widget.SpacingItemDecoration


class MainActivity : AppCompatActivity(),MovieAdapter.Interaction {

    private lateinit var prefs: SharedPreferences
    private lateinit var viewModel: MainViewModel
    private val movieAdapter: MovieAdapter by lazy { MovieAdapter(this) }
    private lateinit var binding: ActivityMainBinding
    var sortBy = Constants.popularity
    var hasConfig = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prefs = PreferenceHelper.defaultPrefs(this)

        val sortByOpt:String? = prefs[Constants.selectedSort]

        sortByOpt?.let{ sortBy = it}

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initViewModel()

        setUpRecycler()

        initObservers()

        binding.apply {
            viewModel = viewModel
            lifecycleOwner = this@MainActivity
        }

        checkConfig()
    }

    private fun checkConfig() {
        val hasConfigStr:Boolean? = prefs [Constants.hasConfig]
        hasConfigStr?.let{ hasConfig = it}
        if(hasConfig){
            viewModel.fetchMovies(sortBy)
        }else{
            viewModel.fetchConfig()
        }

    }

    private fun initViewModel() {
        val factory = InjectorUtils.provideMainViewModelFactory()
        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
    }

    private fun setUpRecycler() {
        binding.recyclerView.adapter = movieAdapter
        binding.recyclerView.addItemDecoration(
            SpacingItemDecoration(
                2,
                Helpers.dpToPx(this, 8),
                true
            )
        )
        binding.recyclerView.setHasFixedSize(true)
    }

    private fun initObservers() {

        binding.refresher.setOnRefreshListener { viewModel.fetchMovies(sortBy) }

        viewModel.getData().observe(this, Observer {
            movieAdapter.submitList(it)
        })

        viewModel.getConfig().observe(this, Observer { resp ->
            val baseurl = resp.images.secure_base_url
            val imageSizelist = resp.images.poster_sizes
            val imagesize = imageSizelist.filter { x -> x.startsWith("w3") }.first()
            val imageurl = baseurl + imagesize
            prefs[Constants.imageUrl] = imageurl
            prefs[Constants.hasConfig] = true
            //config gotten
            viewModel.fetchMovies(sortBy)
        })

        viewModel.getLoader().observe(this, Observer {
            binding.refresher.isRefreshing = it
        })

        viewModel.getErrors().observe(this, Observer { result ->
            showToast(result, Toast.LENGTH_LONG)
        })
    }

    override fun movieClicked(movie: Movie) {
        val intent = Intent(this,MovieDetailActivity::class.java)
        intent.putExtra("movie",movie)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return (when (item.itemId) {
            R.id.action_rating -> {
                setSort(Constants.rating)
                true
            }
            R.id.action_popularity -> {
                setSort(Constants.popularity)
                true
            }
            else ->
                super.onOptionsItemSelected(item)
        })
    }

    private fun setSort(_sortBy: String) {
        sortBy = _sortBy
        prefs[Constants.selectedSort] = sortBy
        invalidateOptionsMenu()
        viewModel.fetchMovies(sortBy)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val ratingIcon = menu?.getItem(0)?.icon
        val popularIcon = menu?.getItem(1)?.icon

        if (ratingIcon != null) {
            ratingIcon.mutate()
            if (sortBy == Constants.rating) {
                ratingIcon.setColorFilter(
                    ContextCompat.getColor(this,R.color.yellow),
                    PorterDuff.Mode.SRC_ATOP
                )
            } else {
                ratingIcon.setColorFilter(
                    ContextCompat.getColor(this,R.color.white),
                    PorterDuff.Mode.SRC_ATOP
                )
            }
        }

        if (popularIcon != null) {
            popularIcon.mutate()
            if (sortBy == Constants.popularity) {
                popularIcon.setColorFilter(
                    ContextCompat.getColor(this,R.color.yellow),
                    PorterDuff.Mode.SRC_ATOP
                )
            } else {
                popularIcon.setColorFilter(
                    ContextCompat.getColor(this,R.color.white),
                    PorterDuff.Mode.SRC_ATOP
                )
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }
    

}
