package com.example.jmonews.data.repository

import com.example.jmonews.data.model.news.APIResponse
import com.example.jmonews.data.repository.dataSource.NewsRemoteDataSource
import com.example.jmonews.data.util.Resource
import com.example.jmonews.domain.repository.NewsRepository
import retrofit2.Response

class NewsRepositoryImpl(private val newsRemoteDataSource: NewsRemoteDataSource) : NewsRepository {

	override suspend fun getNewsHeadlines(page: Int): Resource<APIResponse> {
		return responseToResource(newsRemoteDataSource.getNewsHeadlines(page))
	}

	override suspend fun getNewsTopHeadline(): Resource<APIResponse> {
		return responseToResource(newsRemoteDataSource.getTopNewsHeadlines())
	}

	private fun responseToResource(response: Response<APIResponse>): Resource<APIResponse> {
		if (response.isSuccessful) {
			response.body()?.let { result ->
				return Resource.Success(result)
			}
		}
		return Resource.Error(response.message())
	}
}