package com.app.tentwentyassignment.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.app.tentwentyassignment.MoviesListModel
import com.app.tentwentyassignment.movieDetails.data.MovieDetailModel
import com.app.tentwentyassignment.movieDetails.data.MovieImagesModel
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @GET("upcoming")
    fun getAllMovies(
        @Query("api_key") api_key: String,
    ): Call<MoviesListModel>

    @GET("{movieId}")
    fun getMovieDetails(
        @Path("movieId") movieId: String,
        @Query("api_key") api_key: String
    ): Call<MovieDetailModel>

    @GET("{movieId}/images")
    fun getMovieImages(
        @Path("movieId") movieId: String,
        @Query("api_key") api_key: String
    ): Call<MovieImagesModel>


    companion object {

        private lateinit var context: Context

        fun setContext(con: Context) {
            context=con
        }

        fun getInstance() : RetrofitService {
            var retrofitService: RetrofitService? = null

            val cacheSize = (5 * 1024 * 1024).toLong()

            val myCache = Cache(context.cacheDir, cacheSize)

            val okHttpClient = OkHttpClient.Builder()
                .cache(myCache)
                .addInterceptor { chain ->
                    var request = chain.request()
                    request = if (hasNetwork(context)!!)
                        request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                    else
                        request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 0.005).build()
                    chain.proceed(request)
                }
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            retrofitService = retrofit.create(RetrofitService::class.java)

            return retrofitService!!
        }

        fun hasNetwork(context: Context): Boolean? {
            var isConnected: Boolean? = false // Initial Value
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            if (activeNetwork != null && activeNetwork.isConnected)
                isConnected = true
            return isConnected
        }
    }



}