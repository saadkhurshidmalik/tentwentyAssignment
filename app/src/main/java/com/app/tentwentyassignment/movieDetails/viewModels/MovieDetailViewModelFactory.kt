package com.app.tentwentyassignment.movieDetails.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tentwentyassignment.api.MoviesListRepository

class MovieDetailViewModelFactory constructor(private val repository: MoviesListRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MovieDetailViewModel::class.java)) {
            MovieDetailViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}