package com.example.jmonews.domain.usecase

import com.example.jmonews.data.model.news.APIResponse
import com.example.jmonews.data.util.Resource
import com.example.jmonews.domain.repository.NewsRepository

class GetNewsHeadlinesUseCase(private val newsRepository: NewsRepository) {
	suspend fun execute(page: Int): Resource<APIResponse> = newsRepository.getNewsHeadlines(page)
}