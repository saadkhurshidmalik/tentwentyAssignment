package com.app.tentwentyassignment.moviesList.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.tentwentyassignment.api.MoviesListRepository
import com.app.tentwentyassignment.MoviesListModel
import com.app.tentwentyassignment.ResultsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesListViewModel constructor(private val repository: MoviesListRepository)  : ViewModel() {

    val movieList = MutableLiveData<List<ResultsItem>>()
    val errorMessage = MutableLiveData<String>()

    fun getAllMovies() {

        val response = repository.getAllMovies()
        response.enqueue(object : Callback<MoviesListModel> {
            override fun onResponse(call: Call<MoviesListModel>, response: Response<MoviesListModel>) {
                movieList.postValue(response.body()?.results as List<ResultsItem>?)
            }

            override fun onFailure(call: Call<MoviesListModel>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}