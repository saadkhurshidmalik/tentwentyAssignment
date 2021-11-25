package com.app.tentwentyassignment.api

class MoviesListRepository constructor(private val retrofitService: RetrofitService) {

    fun getAllMovies() = retrofitService.getAllMovies("3d91b3cb8604b40305768acbafe20559")
    fun getMovieDetails(movieId:String) = retrofitService.getMovieDetails(movieId,"3d91b3cb8604b40305768acbafe20559")
    fun getMovieImages(movieId:String) = retrofitService.getMovieImages(movieId,"3d91b3cb8604b40305768acbafe20559")

}