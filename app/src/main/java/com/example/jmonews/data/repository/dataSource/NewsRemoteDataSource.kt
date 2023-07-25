package com.example.jmonews.data.repository.dataSource

import com.example.jmonews.data.model.news.APIResponse
import retrofit2.Response

interface NewsRemoteDataSource {
	suspend fun getNewsHeadlines(page: Int): Response<APIResponse>
	suspend fun getTopNewsHeadlines(): Response<APIResponse>
}