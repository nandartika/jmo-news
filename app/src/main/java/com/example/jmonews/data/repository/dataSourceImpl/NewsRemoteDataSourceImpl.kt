package com.example.jmonews.data.repository.dataSourceImpl

import com.example.jmonews.data.api.NewsApiService
import com.example.jmonews.data.model.news.APIResponse
import com.example.jmonews.data.repository.dataSource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(private val newsApiService: NewsApiService) :
	NewsRemoteDataSource {
	override suspend fun getNewsHeadlines(page: Int): Response<APIResponse> {
		return newsApiService.getNewsHeadlines(page)
	}

	override suspend fun getTopNewsHeadlines(): Response<APIResponse> {
		return newsApiService.getTopNewsHeadlines()
	}
}