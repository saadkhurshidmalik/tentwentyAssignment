package com.app.tentwentyassignment.movieDetails.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.tentwentyassignment.movieDetails.data.MovieDetailModel
import com.app.tentwentyassignment.api.MoviesListRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailViewModel constructor(private val repository: MoviesListRepository)  : ViewModel() {

    val movieList = MutableLiveData<MovieDetailModel>()
    val errorMessage = MutableLiveData<String>()

    fun getMovieDetail(movieId:String) {

        val response = repository.getMovieDetails(movieId)
        response.enqueue(object : Callback<MovieDetailModel> {
            override fun onResponse(call: Call<MovieDetailModel>, response: Response<MovieDetailModel>) {
                movieList.postValue(response.body() )
            }

            override fun onFailure(call: Call<MovieDetailModel>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}