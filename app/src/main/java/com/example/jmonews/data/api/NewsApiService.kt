package com.example.jmonews.data.api

import com.example.jmonews.BuildConfig
import com.example.jmonews.data.model.news.APIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
	@GET("v2/everything")
	suspend fun getNewsHeadlines(
		@Query("page") page: Int,
		@Query("q") q: String = "health",
		@Query("pageSize") pageSize: Int = 10,
		@Query("language") language: String = "en",
		@Query("apiKey") apiKey: String = BuildConfig.API_KEY
	): Response<APIResponse>

	@GET("v2/top-headlines")
	suspend fun getTopNewsHeadlines(
		@Query("pageSize") pageSize: Int = 1,
		@Query("language") language: String = "en",
		@Query("apiKey") apiKey: String = BuildConfig.API_KEY
	): Response<APIResponse>
}