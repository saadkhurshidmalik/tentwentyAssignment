package com.app.tentwentyassignment.movieDetails.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.app.tentwentyassignment.R
import com.app.tentwentyassignment.api.RetrofitService
import com.app.tentwentyassignment.databinding.ActivityMovieDetailBinding
import com.app.tentwentyassignment.movieDetails.viewModels.MovieDetailViewModelFactory
import com.app.tentwentyassignment.api.MoviesListRepository
import com.app.tentwentyassignment.movieDetails.viewModels.MovieDetailViewModel
import com.app.tentwentyassignment.movieDetails.viewModels.MovieImagesViewModel
import com.app.tentwentyassignment.movieDetails.viewModels.MovieImagesViewModelFactory

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding

    lateinit var movieDetailViewModel: MovieDetailViewModel
    lateinit var movieImagesViewModel: MovieImagesViewModel

    private val retrofitService = RetrofitService.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setObservers()

    }

    private fun setObservers() {

        // movie details
        movieDetailViewModel = ViewModelProvider(
            this,
            MovieDetailViewModelFactory(MoviesListRepository(retrofitService))
        ).get(
            MovieDetailViewModel::class.java
        )

        movieDetailViewModel.movieList.observe(this, Observer {
            Log.e("TAG", "onCreateSAAD: $it")
            binding.movieDetailProgressBar.visibility = View.GONE
            if (it != null) {


                binding.movieDetailTitleTv.text = it.originalTitle
                binding.movieDetailDateTv.text = it.releaseDate
                binding.movieDetailOverviewTv.text = it.overview

                var generes: String = ""

                for (i in 0 until it.genres?.size!!) {
                    if (i != (it.genres.size) -1 ) {
                        generes += it.genres[i]?.name + ", "
                    } else {
                        generes += it.genres[i]?.name
                    }

                }
                binding.movieDetailGenreTv.text = generes


            } else {
                Toast.makeText(
                    this,
                    "No response from server, please check your internet connection",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        movieDetailViewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
        movieDetailViewModel.getMovieDetail(intent.extras?.getString("movieId")!!)


        // movie images
        movieImagesViewModel = ViewModelProvider(
            this,
            MovieImagesViewModelFactory(MoviesListRepository(retrofitService))
        ).get(
            MovieImagesViewModel::class.java
        )

        movieImagesViewModel.imagesList.observe(this, Observer {
            Log.e("TAG", "onCreateSAAD: $it")
            if (it != null) {
                val imageList = ArrayList<SlideModel>() // Create image list

                for (i in 0..it.backdrops?.size!!) {
                    if (i < 5) {
                        imageList.add(
                            SlideModel(
                                "https://image.tmdb.org/t/p/original/" + it.backdrops!![i]?.filePath,
                                ""
                            )
                        )
                    }
                }

                val imageSlider = findViewById<ImageSlider>(R.id.image_slider)
                imageSlider.setImageList(imageList)
            } else {
                Toast.makeText(
                    this,
                    "No response from server, please check your internet connection",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        movieImagesViewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
        movieImagesViewModel.getMovieImages(intent.extras?.getString("movieId")!!)

    }
}