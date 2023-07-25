package com.example.jmonews.presentation.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jmonews.data.model.home.ProgramServiceItem
import com.example.jmonews.databinding.ComponentEmptySectionBinding
import com.example.jmonews.databinding.ItemServiceProgramBinding
import com.example.jmonews.databinding.ItemServiceProgramButtonBinding
import com.example.jmonews.presentation.BaseViewHolder

class ProgramServiceAdapter(
	private val programServiceData: List<ProgramServiceItem>,
	private val menuClick: ((data: String) -> Unit)? = null,
	private val otherClick: (() -> Unit)? = null
) : RecyclerView.Adapter<BaseViewHolder<ProgramServiceItem>>() {
	companion object {
		private const val item = 0
		private const val other = 1
	}

	override fun onCreateViewHolder(
		parent: ViewGroup, viewType: Int
	): BaseViewHolder<ProgramServiceItem> {
		return when (viewType) {
			item -> {
				val view = ItemServiceProgramBinding.inflate(
					LayoutInflater.from(parent.context), parent, false
				)
				ItemViewHolder(view)
			}

			other -> {
				val view = ItemServiceProgramButtonBinding.inflate(
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

	override fun onBindViewHolder(holder: BaseViewHolder<ProgramServiceItem>, position: Int) {
		holder.bind(if (position < programServiceData.size) programServiceData[position] else null)
	}

	override fun getItemCount(): Int = programServiceData.size + 1

	override fun getItemViewType(position: Int): Int {
		return if (position >= programServiceData.size) other else item
	}

	inner class ItemViewHolder(private val binding: ItemServiceProgramBinding) :
		BaseViewHolder<ProgramServiceItem>(binding) {
		override fun bind(data: ProgramServiceItem?) {
			binding.apply {
				tvHeader.text = data?.title
				tvCaption.text = data?.caption
				data?.iconId?.let { icon.setImageResource(it) }
				cvServiceProgram.setOnClickListener {
					menuClick?.invoke(data?.title ?: "Title tidak ada")
				}
			}
		}
	}

	inner class OtherViewHolder(private val binding: ItemServiceProgramButtonBinding) :
		BaseViewHolder<ProgramServiceItem>(binding) {
		override fun bind(data: ProgramServiceItem?) {
			binding.button.setOnClickListener {
				otherClick?.invoke()
			}
		}
	}

	inner class EmptyViewHolder(binding: ComponentEmptySectionBinding) :
		BaseViewHolder<ProgramServiceItem>(binding) {
		override fun bind(data: ProgramServiceItem?) {
		}
	}
}