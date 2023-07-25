package com.example.jmonews.presentation.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.jmonews.R
import com.example.jmonews.data.model.news.Article
import com.example.jmonews.databinding.ActivityNewsDetailBinding

class NewsDetailActivity : AppCompatActivity() {
	private lateinit var binding: ActivityNewsDetailBinding
	private lateinit var newsDetailViewModel: NewsDetailViewModel

	companion object {
		const val EXTRA_NEWS_ITEM = "extra_news_item"
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityNewsDetailBinding.inflate(layoutInflater)
		setContentView(binding.root)

		newsDetailViewModel = ViewModelProvider(this)[NewsDetailViewModel::class.java]

//		getIntentExtra()
//		variableObserve()
	}

//	private fun variableObserve() {
//		newsDetailViewModel.newsItem.observe(this) { item ->
//			binding.titleTextView.text = item.title
//			binding.descriptionTextView.text = item.description
//		}
//	}
//
//	private fun getIntentExtra() {
//		val newsItem = intent.getParcelableExtra(EXTRA_NEWS_ITEM, )
//		newsItem?.let {
//			newsDetailViewModel.setNewsItem(it)
//		}
//	}
}