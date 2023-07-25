package com.example.jmonews.presentation.main.news

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.jmonews.data.api.NewsApiService
import com.example.jmonews.data.api.RetrofitInstance
import com.example.jmonews.data.model.news.Article
import com.example.jmonews.data.repository.NewsRepositoryImpl
import com.example.jmonews.data.repository.dataSourceImpl.NewsRemoteDataSourceImpl
import com.example.jmonews.data.util.Resource
import com.example.jmonews.data.util.dateConverter
import com.example.jmonews.databinding.FragmentNewsBinding
import com.example.jmonews.domain.usecase.GetNewsHeadlinesUseCase
import com.example.jmonews.domain.usecase.GetNewsTopHeadlineUseCase
import com.example.jmonews.presentation.main.news.adapter.NewsHeadlinesAdapter
import com.example.jmonews.presentation.news.NewsDetailActivity
import com.google.gson.Gson

class NewsFragment : Fragment() {
	private var _binding: FragmentNewsBinding? = null
	private val binding get() = _binding!!
	private lateinit var newsViewModel: NewsViewModel
	private var newsHeadlineAdapter = NewsHeadlinesAdapter {
		moveToDetailActivity(it)
	}
	private var page = 1
	private var isLoading = false
	private var isInit = false

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
	): View {
		_binding = FragmentNewsBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setupViewModel()
		setupNewsHeadline()
		setupNewsTopHeadline()
	}

	private fun setupNewsHeadline() {
		binding.componentTitleNewsTopHeadline.tvTitle.text = "Berita Utama"

		newsViewModel.newsTopHeadlines.observe(viewLifecycleOwner) { response ->
			when (response) {
				is Resource.Success -> {
					response.data?.articles?.first().let {article ->
						binding.componentItemNews.tvTitle.text = article?.title
						binding.componentItemNews.tvDate.text =
							if (article?.publishedAt != null) dateConverter(article?.publishedAt) else "Waktu publikasi tidak diketahui"
						Glide.with(requireActivity()).load(article?.urlToImage)
							.apply(RequestOptions.centerCropTransform()) // Optional: You can apply transformations if needed
							.into(binding.componentItemNews.image)

						binding.componentItemNews.cvNewsItem.setOnClickListener { _ ->
							article?.let { moveToDetailActivity(it) }
						}
					}
				}

				is Resource.Loading -> {
					binding.componentItemNews.tvTitle.text = "Loading ..."
					binding.componentItemNews.tvDate.text = "Loading ..."
				}

				is Resource.Error -> {
					// TODO : Create Popup
				}
			}
		}

		newsViewModel.getNewsTopHeadline()
	}

	private fun setupNewsTopHeadline() {
		initRecyclerView()
		binding.componentTitleNewsHeadline.tvTitle.text = "Berita Lainnya"

		newsViewModel.newsHeadlines.observe(viewLifecycleOwner) { response ->
			when (response) {
				is Resource.Success -> {
					isLoading = false
					response.data?.let {
						newsHeadlineAdapter.add(it.articles)
					}
				}

				is Resource.Loading -> {
					isLoading = true
				}

				is Resource.Error -> {
					isLoading = false
					// TODO : Create Popup
				}
			}
		}

		newsViewModel.getNewsHeadlines(page)
	}

	private fun initRecyclerView() {
		binding.rvNewsHeadline.adapter = newsHeadlineAdapter
		binding.rvNewsHeadline.addOnScrollListener(onScrollListener)
	}

	private val onScrollListener = object : RecyclerView.OnScrollListener() {
		override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
			super.onScrolled(recyclerView, dx, dy)
			if (isLoading) return

			(recyclerView.layoutManager as? GridLayoutManager?)?.let {
				val sizeOfTheCurrentList = it.itemCount
				val visibleItems = it.childCount
				val topPosition = it.findFirstVisibleItemPosition()

				val hasReachedToEnd = topPosition + visibleItems >= sizeOfTheCurrentList
				val shouldPaginate = !isLoading && hasReachedToEnd

				if (shouldPaginate) {
					page++
					newsViewModel.getNewsHeadlines(page)
				}
			}
		}
	}

	private fun setupViewModel() {
		val retService = RetrofitInstance.getRetrofitInstance().create(NewsApiService::class.java)
		val remoteDataSource = NewsRemoteDataSourceImpl(retService)
		val repository = NewsRepositoryImpl(remoteDataSource)

		val getNewsHeadlinesUseCase = GetNewsHeadlinesUseCase(repository)
		val getNewsTopHeadlineUseCase = GetNewsTopHeadlineUseCase(repository)

		val factory = NewsViewModelFactory(
			requireActivity().application, getNewsHeadlinesUseCase, getNewsTopHeadlineUseCase
		)
		newsViewModel = ViewModelProvider(this, factory)[NewsViewModel::class.java]
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	private fun moveToDetailActivity(article: Article) {
		val gson = Gson()
		val data = gson.toJson(article)

		val intent = Intent(activity, NewsDetailActivity::class.java)
		intent.apply {
			putExtra(NewsDetailActivity.EXTRA_NEWS_ITEM, data)
			flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
		}
		startActivity(intent)
	}
}