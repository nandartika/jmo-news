package com.example.jmonews.presentation.main.news.section

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jmonews.R
import com.example.jmonews.data.util.Resource
import com.example.jmonews.databinding.ComponentNewsHeadlinesSectionBinding
import com.example.jmonews.presentation.main.news.NewsViewModel
import com.example.jmonews.presentation.main.news.adapter.NewsHeadlinesAdapter
import com.xwray.groupie.viewbinding.BindableItem

class NewsHeadlinesSection(
	private val lifecycleOwner: LifecycleOwner,
	viewModelStoreOwner: ViewModelStoreOwner,
) : BindableItem<ComponentNewsHeadlinesSectionBinding>() {
	private val adapter = NewsHeadlinesAdapter(onNewsClicked = {
		// TODO Add Callback
	})
	private var page = 1
	private var isLoading = false
	private var isInit = false
	private var newsViewModel: NewsViewModel? = null

	init {
		newsViewModel = ViewModelProvider(viewModelStoreOwner)[NewsViewModel::class.java]
	}

	fun setupObserver() {
		newsViewModel?.newsHeadlines?.observe(lifecycleOwner) { response ->
			when (response) {
				is Resource.Success -> {
					isLoading = false
					response.data?.let {
						adapter.add(it.articles)
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

		newsViewModel?.getNewsHeadlines(page)
	}

	override fun bind(viewBinding: ComponentNewsHeadlinesSectionBinding, position: Int) {
		if (isInit) return
		isInit = true
		viewBinding.apply {
			initRecyclerView(this.rvNewsHeadline)
			rvNewsHeadline.addOnScrollListener(onScrollListener)

			componentTitle.tvTitle.text = "Berita Lainnya"
		}
	}

	override fun getLayout(): Int = R.layout.component_news_headlines_section

	override fun initializeViewBinding(view: View): ComponentNewsHeadlinesSectionBinding =
		ComponentNewsHeadlinesSectionBinding.bind(view)

	private fun initRecyclerView(recyclerView: RecyclerView) {
		recyclerView.adapter = adapter

	}

	private val onScrollListener = object : RecyclerView.OnScrollListener() {
		override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
			super.onScrolled(recyclerView, dx, dy)
			if (isLoading) return

			(recyclerView.layoutManager as? GridLayoutManager?)?.let {
				val lastIndex = adapter.itemCount - 1
				val lastIndexVisibleItem = it.findLastCompletelyVisibleItemPosition()
				if (lastIndex < lastIndexVisibleItem) return
				page += 1
				newsViewModel?.getNewsHeadlines(page)
			}
		}
	}
}