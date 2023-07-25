package com.example.jmonews.presentation.main.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView.OnScrollListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.jmonews.data.api.NewsApiService
import com.example.jmonews.data.api.RetrofitInstance
import com.example.jmonews.data.repository.NewsRepositoryImpl
import com.example.jmonews.data.repository.dataSourceImpl.NewsRemoteDataSourceImpl
import com.example.jmonews.databinding.FragmentNewsBinding
import com.example.jmonews.domain.usecase.GetNewsHeadlinesUseCase
import com.example.jmonews.domain.usecase.GetNewsTopHeadlineUseCase
import com.example.jmonews.presentation.main.news.section.NewsHeadlinesSection
import com.example.jmonews.presentation.main.news.section.NewsTopHeadlineSection
import com.xwray.groupie.GroupieAdapter

class NewsFragment : Fragment() {
	private var _binding: FragmentNewsBinding? = null
	private val binding get() = _binding!!

	private lateinit var newsViewModel: NewsViewModel
	private var newsAdapter: GroupieAdapter? = null
	private var isLoading = false

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentNewsBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setupViewModel()
		setupView()
	}

	private fun setupViewModel() {
		val retService = RetrofitInstance.getRetrofitInstance().create(NewsApiService::class.java)
		val remoteDataSource = NewsRemoteDataSourceImpl(retService)
		val repository = NewsRepositoryImpl(remoteDataSource)

		val getNewsHeadlinesUseCase = GetNewsHeadlinesUseCase(repository)
		val getNewsTopHeadlineUseCase = GetNewsTopHeadlineUseCase(repository)

		val factory = NewsViewModelFactory(
			requireActivity().application,
			getNewsHeadlinesUseCase,
			getNewsTopHeadlineUseCase
		)
		newsViewModel = ViewModelProvider(this, factory)[NewsViewModel::class.java]
	}

	private fun setupView() {
		newsViewModel.getNewsTopHeadline()

		newsAdapter = GroupieAdapter()
		newsAdapter?.apply {
			clear()
			add(NewsTopHeadlineSection(requireActivity(), newsViewModel.newsTopHeadlines))
			val newsHeadlinesSection = NewsHeadlinesSection(viewLifecycleOwner, this@NewsFragment)
			newsHeadlinesSection.setupObserver()
			add(newsHeadlinesSection)
		}
		binding.rvNews.apply {
			addOnScrollListener(object : RecyclerView.OnScrollListener() {
				override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
					super.onScrolled(recyclerView, dx, dy)
					Log.i("APA", "onScrolled: trigre")
				}
			})
			adapter = newsAdapter
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}