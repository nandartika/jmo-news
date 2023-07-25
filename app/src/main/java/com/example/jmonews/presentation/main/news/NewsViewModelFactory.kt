package com.example.jmonews.presentation.main.news

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jmonews.domain.usecase.GetNewsHeadlinesUseCase
import com.example.jmonews.domain.usecase.GetNewsTopHeadlineUseCase

class NewsViewModelFactory(
	private val app: Application,
	private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
	private val getNewsTopHeadlineUseCase: GetNewsTopHeadlineUseCase
) : ViewModelProvider.Factory {
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		return NewsViewModel(app, getNewsHeadlinesUseCase, getNewsTopHeadlineUseCase) as T
	}
}