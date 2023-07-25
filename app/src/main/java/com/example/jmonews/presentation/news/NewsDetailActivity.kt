package com.example.jmonews.presentation.news

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.jmonews.data.model.news.Article
import com.example.jmonews.data.util.dateConverter
import com.example.jmonews.databinding.ActivityNewsDetailBinding
import com.google.gson.Gson

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

		setSupportActionBar(binding.toolbar)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		newsDetailViewModel = ViewModelProvider(this)[NewsDetailViewModel::class.java]

		getIntentExtra()
		variableObserve()
	}

	private fun variableObserve() {
		newsDetailViewModel.newsItem.observe(this) { item ->
			binding.tvTitle.text = item.title
			binding.tvDescription.text = item.description
			binding.tvDate.text = dateConverter(item.publishedAt)
			Glide.with(this).load(item.urlToImage).into(binding.image)
		}
	}

	private fun getIntentExtra() {
		val articleJson = intent.getStringExtra(EXTRA_NEWS_ITEM)
		val gson = Gson()
		val article = gson.fromJson(articleJson, Article::class.java)

		article?.let {
			newsDetailViewModel.setNewsItem(it)
		}
	}
}