package com.example.jmonews.domain.repository

import com.example.jmonews.data.model.news.APIResponse
import com.example.jmonews.data.util.Resource

interface NewsRepository {
	suspend fun getNewsHeadlines(page: Int): Resource<APIResponse>
	suspend fun getNewsTopHeadline(): Resource<APIResponse>
}