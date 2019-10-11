package com.martiniriga.moviedb.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.martiniriga.moviedb.data.gson.Movie
import com.martiniriga.moviedb.databinding.ListItemMovieBinding
import com.martiniriga.moviedb.viewmodels.MovieItemViewModel


class MovieAdapter(private val interaction: Interaction? = null) :
    ListAdapter<Movie, MovieAdapter.ViewHolder>(EventDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        holder.apply {
            bind(movie)
            itemView.tag = movie
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            interaction
        )
    }

    interface Interaction {
        fun movieClicked(movie: Movie)
    }


    class ViewHolder(
        private val binding: ListItemMovieBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Movie) {
            binding.apply {
                viewModel = MovieItemViewModel(item)
                setClickListener { interaction?.movieClicked(item) }
                executePendingBindings()
            }
        }
    }
}

private class EventDiffCallback : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}