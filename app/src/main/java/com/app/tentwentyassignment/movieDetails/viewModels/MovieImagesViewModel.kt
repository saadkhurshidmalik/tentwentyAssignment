package com.app.tentwentyassignment.movieDetails.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.tentwentyassignment.api.MoviesListRepository
import com.app.tentwentyassignment.movieDetails.data.MovieImagesModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieImagesViewModel constructor(private val repository: MoviesListRepository)  : ViewModel() {

    val imagesList = MutableLiveData<MovieImagesModel>()
    val errorMessage = MutableLiveData<String>()

    fun getMovieImages(movieId:String) {

        val response = repository.getMovieImages(movieId)
        response.enqueue(object : Callback<MovieImagesModel> {
            override fun onResponse(call: Call<MovieImagesModel>, response: Response<MovieImagesModel>) {
                imagesList.postValue(response.body() )
            }

            override fun onFailure(call: Call<MovieImagesModel>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}