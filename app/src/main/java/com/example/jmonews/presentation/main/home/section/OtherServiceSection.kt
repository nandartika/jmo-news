package com.example.jmonews.presentation.main.home.section

import android.view.View
import com.example.jmonews.R
import com.example.jmonews.data.model.home.OtherServiceItem
import com.example.jmonews.databinding.ComponentOtherServiceSectionBinding
import com.example.jmonews.presentation.main.home.adapter.OtherServiceAdapter
import com.xwray.groupie.viewbinding.BindableItem

class OtherServiceSection(private val otherServiceData: List<OtherServiceItem>) : BindableItem<ComponentOtherServiceSectionBinding>() {
	lateinit var adapter : OtherServiceAdapter

	override fun bind(viewBinding: ComponentOtherServiceSectionBinding, position: Int) {
		viewBinding.apply {
			componentTitle.tvTitle.text = "Layanan Lainnya"
			adapter = OtherServiceAdapter(otherServiceData)
			rvNewsHeadline.adapter = adapter
		}
	}

	override fun getLayout(): Int = R.layout.component_other_service_section

	override fun initializeViewBinding(view: View): ComponentOtherServiceSectionBinding =
		ComponentOtherServiceSectionBinding.bind(view)
}