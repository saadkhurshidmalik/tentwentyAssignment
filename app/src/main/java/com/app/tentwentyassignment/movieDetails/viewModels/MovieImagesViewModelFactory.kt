package com.app.tentwentyassignment.movieDetails.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tentwentyassignment.api.MoviesListRepository

class MovieImagesViewModelFactory constructor(private val repository: MoviesListRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MovieImagesViewModel::class.java)) {
            MovieImagesViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}