
package com.martiniriga.moviedb.services

import com.martiniriga.moviedb.data.gson.ConfigurationResponse
import com.martiniriga.moviedb.data.gson.MoviesResponse
import retrofit2.Call
import retrofit2.http.*


/**
 * API communication setup
 */
interface ApiService {

//?sort_by=popularity.desc&api_key=2d491cc2b62292dd7817b8b544aa5dab"

    @GET("discover/movie")
    fun movies(@Query("sort_by") sort_by:String?): Call<MoviesResponse>

    @GET("configuration")
    fun configs(): Call<ConfigurationResponse>


//    @GET("movie/{id}?api_key=2d491cc2b62292dd7817b8b544aa5dab")
//    fun eventdetails(@Path(value = "id", encoded = true) id: Int,@Header("Authorization") authHeader: String): Call<EventDetailResponse>
//
//
//
//    @FormUrlEncoded
//    @POST("topics/follow")
//    fun followTopic(@Field("tag") tag: String, @Header("Authorization") authHeader: String): Call<TopicResponse>


}