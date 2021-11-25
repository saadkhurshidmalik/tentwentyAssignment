package com.app.tentwentyassignment.moviesList.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tentwentyassignment.api.MoviesListRepository

class MoviesListViewModelFactory constructor(private val repository: MoviesListRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MoviesListViewModel::class.java)) {
            MoviesListViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}