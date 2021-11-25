package com.app.tentwentyassignment.moviesList.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.tentwentyassignment.api.MoviesListRepository
import com.app.tentwentyassignment.R
import com.app.tentwentyassignment.api.RetrofitService
import com.app.tentwentyassignment.databinding.ActivityMoviesListBinding
import com.app.tentwentyassignment.moviesList.adapters.MoviesListAdapter
import com.app.tentwentyassignment.moviesList.viewModels.MoviesListViewModel
import com.app.tentwentyassignment.moviesList.viewModels.MoviesListViewModelFactory

class MoviesListActivity : AppCompatActivity() {
    private lateinit var moviesListBinding: ActivityMoviesListBinding

    lateinit var viewModel: MoviesListViewModel

    val adapter = MoviesListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_list)
        moviesListBinding = ActivityMoviesListBinding.inflate(layoutInflater)
        setContentView(moviesListBinding.root)

        RetrofitService.setContext(this);

        initViews()

    }

    private fun initViews() {
         val retrofitService = RetrofitService.getInstance()

        viewModel = ViewModelProvider(this, MoviesListViewModelFactory(MoviesListRepository(retrofitService))).get(
            MoviesListViewModel::class.java)

        moviesListBinding.recyclerview.adapter = adapter

        viewModel.movieList.observe(this, Observer {
            Log.e("TAG", "onCreate: $it")
            moviesListBinding.movieListProgressBar.visibility = View.GONE
            if(it!=null)
            {
                adapter.setMovieList(it)
            }
            else{
                Toast.makeText(this, "No response from server, please check your internet connection", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.getAllMovies()
    }
}