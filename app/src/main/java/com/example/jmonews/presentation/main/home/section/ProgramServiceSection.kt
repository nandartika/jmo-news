package com.example.jmonews.presentation.main.home.section

import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.example.jmonews.R
import com.example.jmonews.data.model.home.ProgramServiceItem
import com.example.jmonews.databinding.ComponentServiceProgramSectionBinding
import com.example.jmonews.presentation.main.home.adapter.ProgramServiceAdapter
import com.xwray.groupie.viewbinding.BindableItem

class ProgramServiceSection(
	private val context: FragmentActivity, private val programServiceData: List<ProgramServiceItem>
) : BindableItem<ComponentServiceProgramSectionBinding>() {

	private lateinit var adapter: ProgramServiceAdapter

	override fun bind(viewBinding: ComponentServiceProgramSectionBinding, position: Int) {
		viewBinding.apply {
			componentTitle.tvTitle.text = "Layanan Service"
			adapter = ProgramServiceAdapter(programServiceData, {
				Toast.makeText(context, "Menu Click Position: $it", Toast.LENGTH_SHORT).show()
			}, {
				Toast.makeText(context, "Other CLick", Toast.LENGTH_SHORT).show()
			})
			rvServiceProgram.adapter = adapter
		}
	}

	override fun getLayout(): Int = R.layout.component_service_program_section

	override fun initializeViewBinding(view: View): ComponentServiceProgramSectionBinding =
		ComponentServiceProgramSectionBinding.bind(view)
}