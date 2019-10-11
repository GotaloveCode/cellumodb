package com.martiniriga.moviedb.services

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    //    3/movie/550?api_key=2d491cc2b62292dd7817b8b544aa5dab"

    //    create logger
    private val logger = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)


    private val authInterceptor = Interceptor { chain ->
        val url = chain.request()
            .url()
            .newBuilder()
            .addQueryParameter("api_key", "2d491cc2b62292dd7817b8b544aa5dab")
            .build()
        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()
        return@Interceptor chain.proceed(request)
    }

    //    create Okhttp client
    private val client = OkHttpClient.Builder().addInterceptor(authInterceptor).addInterceptor(logger)


    private val builder = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client.build())

    private var retrofit = builder.build()

    fun <S> buildService(serviceType: Class<S>): S {
        return retrofit.create(serviceType)
    }


}
