package com.example.jmonews.presentation.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jmonews.data.model.news.Article

class NewsDetailViewModel : ViewModel() {
	private val _newsItem = MutableLiveData<Article>()
	val newsItem: LiveData<Article> = _newsItem

	fun setNewsItem(item: Article) {
		_newsItem.value = item
	}
}