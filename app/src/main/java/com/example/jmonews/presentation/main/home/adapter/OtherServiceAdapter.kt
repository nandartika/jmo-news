package com.example.jmonews.presentation.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jmonews.data.model.home.OtherServiceItem
import com.example.jmonews.databinding.ComponentEmptySectionBinding
import com.example.jmonews.databinding.ItemOtherServiceBinding
import com.example.jmonews.presentation.BaseViewHolder

class OtherServiceAdapter(private val otherServiceData: List<OtherServiceItem>) :
	RecyclerView.Adapter<BaseViewHolder<OtherServiceItem>>() {
	companion object {
		private const val item = 0
		private const val other = 1
	}

	override fun onCreateViewHolder(
		parent: ViewGroup, viewType: Int
	): BaseViewHolder<OtherServiceItem> {
		return when (viewType) {
			item -> {
				val view = ItemOtherServiceBinding.inflate(
					LayoutInflater.from(parent.context), parent, false
				)
				ItemViewHolder(view)
			}

			other -> {
				val view = ItemOtherServiceBinding.inflate(
					LayoutInflater.from(parent.context), parent, false
				)
				OtherViewHolder(view)
			}

			else -> {
				val view = ComponentEmptySectionBinding.inflate(
					LayoutInflater.from(parent.context), parent, false
				)
				EmptyViewHolder(view)
			}
		}
	}

	inner class ItemViewHolder(private val binding: ItemOtherServiceBinding) :
		BaseViewHolder<OtherServiceItem>(binding) {
		override fun bind(data: OtherServiceItem?) {
			binding.apply {
				textView.text = data?.title
			}
		}
	}

	inner class OtherViewHolder(private val binding: ItemOtherServiceBinding) :
		BaseViewHolder<OtherServiceItem>(binding) {
		override fun bind(data: OtherServiceItem?) {
			binding.apply {
				textView.text = data?.title
				data?.iconId?.let { icon.setImageResource(it) }
			}
		}
	}

	inner class EmptyViewHolder(binding: ComponentEmptySectionBinding) :
		BaseViewHolder<OtherServiceItem>(binding) {
		override fun bind(data: OtherServiceItem?) {
		}
	}

	override fun getItemCount(): Int = otherServiceData.size

	override fun onBindViewHolder(holder: BaseViewHolder<OtherServiceItem>, position: Int) {
		holder.bind(otherServiceData[position])
	}

	override fun getItemViewType(position: Int): Int {
		return if (position == otherServiceData.lastIndex) other else item
	}
}