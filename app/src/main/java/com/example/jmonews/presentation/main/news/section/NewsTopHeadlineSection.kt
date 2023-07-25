package com.example.jmonews.presentation.main.news.section

import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.jmonews.R
import com.example.jmonews.data.model.news.APIResponse
import com.example.jmonews.data.util.Resource
import com.example.jmonews.data.util.dateConverter
import com.example.jmonews.databinding.ComponentNewsTopHeadlineSectionBinding
import com.xwray.groupie.viewbinding.BindableItem

class NewsTopHeadlineSection(
	private val context: FragmentActivity,
	private val newsTopHeadlines: MutableLiveData<Resource<APIResponse>>
) : BindableItem<ComponentNewsTopHeadlineSectionBinding>() {
	override fun bind(viewBinding: ComponentNewsTopHeadlineSectionBinding, position: Int) {
		viewBinding.apply {
			componentTitle.tvTitle.text = "Berita Terbaru"
			newsTopHeadlines.observe(context) { response ->
				when (response) {
					is Resource.Success -> {
						val data = response.data?.articles?.first()
						val title = data?.title
						val publishedAt = data?.publishedAt
						val imageUrl = data?.urlToImage

						itemNews.tvTitle.text = title
						itemNews.tvDate.text =
							if (publishedAt != null) dateConverter(publishedAt) else "Waktu publikasi tidak diketahui"
						Glide.with(context)
							.load(imageUrl)
							.apply(RequestOptions.centerCropTransform()) // Optional: You can apply transformations if needed
							.into(itemNews.image)
					}

					is Resource.Loading -> {
						itemNews.tvTitle.text = "Loading ..."
						itemNews.tvDate.text = "Loading ..."
					}

					is Resource.Error -> {
						// TODO : Create Popup
					}
				}
			}
		}
	}

	override fun getLayout(): Int = R.layout.component_news_top_headline_section


	override fun initializeViewBinding(view: View): ComponentNewsTopHeadlineSectionBinding =
		ComponentNewsTopHeadlineSectionBinding.bind(view)

}