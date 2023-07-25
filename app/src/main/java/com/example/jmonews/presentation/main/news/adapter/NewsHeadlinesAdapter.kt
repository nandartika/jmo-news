package com.example.jmonews.presentation.main.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jmonews.data.model.news.Article
import com.example.jmonews.data.util.dateConverter
import com.example.jmonews.databinding.ItemNewsBinding

class NewsHeadlinesAdapter(
	val onNewsClicked: ((article: Article) -> Unit)? = null
) :
	RecyclerView.Adapter<NewsHeadlinesAdapter.ViewHolder>() {
	private val articles = mutableListOf<Article>()

	override fun onCreateViewHolder(
		parent: ViewGroup, viewType: Int
	): ViewHolder {
		val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val article = articles[position]
		holder.bind(article)
	}

	override fun getItemCount(): Int = articles.size

	fun add(articles: List<Article>) {
		this.articles.addAll(articles.toMutableList())
		val positionStart = if (itemCount == 0) 0 else itemCount.minus(1)
		notifyItemRangeInserted(positionStart, articles.size)
	}

	inner class ViewHolder(private val binding: ItemNewsBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bind(article: Article) {
			binding.apply {
				val title = article.title
				val date = dateConverter(article.publishedAt)
				val imageUrl = article.urlToImage

				tvTitle.text = title
				tvDate.text = date
				Glide.with(itemView.context).load(imageUrl).into(image)

				cvNewsItem.setOnClickListener {
					onNewsClicked?.invoke(article)
				}
			}
		}
	}
}