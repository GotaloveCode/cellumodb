

package com.martiniriga.moviedb.data.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.martiniriga.moviedb.data.gson.ConfigurationResponse
import com.martiniriga.moviedb.data.gson.Movie
import com.martiniriga.moviedb.data.gson.MoviesResponse
import com.martiniriga.moviedb.services.ApiService
import com.martiniriga.moviedb.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback

class MovieRepository  {
    private val mError = MutableLiveData<String>()
    private val dataLoader =  MutableLiveData<Boolean>()
    private val myData =  MutableLiveData<List<Movie>>()
    private val mConfig = MutableLiveData<ConfigurationResponse>()


    fun getLoader()  = dataLoader as LiveData<Boolean>

    fun getData()  = myData as LiveData<List<Movie>>

    fun getConfig()  = mConfig as LiveData<ConfigurationResponse>

    fun setLoader(loading:Boolean){
        dataLoader.value = loading
    }

    fun setError(msg:String){
        mError.value = msg
    }

    fun getErrors()  = mError as LiveData<String>


    fun fetchMovies(orderby:String? = "popularity.desc"){
        setLoader(true)
        val apiService = ServiceBuilder.buildService(ApiService::class.java)
        val call: Call<MoviesResponse> = apiService.movies(orderby)
        call.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(call: Call<MoviesResponse>, response: retrofit2.Response<MoviesResponse>) {
                setLoader(false)
                val res = response.body()?.results
                res?.let{ myData.value = it }
            }
            override fun onFailure(call: Call<MoviesResponse>?, t: Throwable?) {
                setLoader(false)
                setError("Could not fetch any movies!")
            }
        })
    }

    fun fetchConfiguration(){
        setLoader(true)
        val apiService = ServiceBuilder.buildService(ApiService::class.java)
        val call: Call<ConfigurationResponse> = apiService.configs()
        call.enqueue(object : Callback<ConfigurationResponse> {
            override fun onResponse(call: Call<ConfigurationResponse>, response: retrofit2.Response<ConfigurationResponse>) {
                setLoader(false)
                val res = response.body()
                res?.let{ mConfig.value = it }
            }
            override fun onFailure(call: Call<ConfigurationResponse>?, t: Throwable?) {
                setLoader(false)
                setError("Could not fetch configurations!")
            }
        })
    }


//    fun favorite(id:Int,token:String){
//        setLoader(true)
//        val apiService = ServiceBuilder.buildService(ApiService::class.java)
//        val call: Call<BlogDetailResponse> = apiService.toggleLikeBlog(id,"Bearer $token")
//        call.enqueue(object : Callback<BlogDetailResponse> {
//            override fun onResponse(call: Call<BlogDetailResponse>, response: retrofit2.Response<BlogDetailResponse>) {
//                val res = response.body()?.data
//                res?.let{
//                    bookmarkData.value = it
//                    setLoader(false)
//                }
//            }
//            override fun onFailure(call: Call<BlogDetailResponse>?, t: Throwable?) {
//                setLoader(false)
//                setError("Failed to add to change like status!")
//            }
//        })
//    }

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: MovieRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: MovieRepository().also { instance = it }
            }
    }
    
}