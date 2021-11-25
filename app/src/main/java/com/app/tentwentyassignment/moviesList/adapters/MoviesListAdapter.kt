package com.app.tentwentyassignment.moviesList.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.tentwentyassignment.ResultsItem
import com.app.tentwentyassignment.databinding.AdapterMovieBinding
import com.app.tentwentyassignment.movieDetails.activities.MovieDetailActivity
import com.squareup.picasso.Picasso

class MoviesListAdapter : RecyclerView.Adapter<MoviesListViewHolder>() {

    var movies = mutableListOf<ResultsItem>()

    fun setMovieList(movies: List<ResultsItem>) {
        this.movies = movies.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesListViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = AdapterMovieBinding.inflate(inflater, parent, false)
        return MoviesListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesListViewHolder, position: Int) {
        val movie = movies[position]
        holder.binding.movieAdapterNameTv.text = movies[position].originalTitle
        holder.binding.movieAdapterDateTv.text = movies[position].releaseDate
        if(movies[position].adult!!)
        {
            holder.binding.movieAdapterAdultTv.text = "Adult"
        }
        else{
            holder.binding.movieAdapterAdultTv.text = "Non Adult"

        }
        Picasso.get().load("https://image.tmdb.org/t/p/original/"+movie.posterPath).into(holder.binding.imageview)

        holder.itemView.setOnClickListener {
            it.context.startActivity(
                Intent(it.context, MovieDetailActivity::class.java)
                    .putExtra("movieId",
                        movies.get(position).id.toString()
                    ))
        }

    }

    override fun getItemCount(): Int {
        return movies.size
    }
}

class MoviesListViewHolder(val binding: AdapterMovieBinding) : RecyclerView.ViewHolder(binding.root) {

}