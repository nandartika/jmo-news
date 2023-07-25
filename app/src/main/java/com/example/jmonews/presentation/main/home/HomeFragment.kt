package com.example.jmonews.presentation.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.jmonews.databinding.FragmentHomeBinding
import com.example.jmonews.presentation.main.home.section.OtherServiceSection
import com.example.jmonews.presentation.main.home.section.ProgramServiceSection
import com.xwray.groupie.GroupieAdapter

class HomeFragment : Fragment() {
	private lateinit var binding: FragmentHomeBinding
	private lateinit var homeViewModel: HomeViewModel
	private var adapter: GroupieAdapter? = null

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentHomeBinding.inflate(layoutInflater)

		setupViewModel()
		setupView()

		return binding.root
	}

	private fun setupViewModel() {
		homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
	}

	private fun setupView() {
		adapter = GroupieAdapter()
		adapter?.apply {
			clear()
			add(ProgramServiceSection(requireActivity(), homeViewModel.getProgramServiceData()))
			add(OtherServiceSection(homeViewModel.getOtherServiceData()))
//			add(NewsHeadlinesSection(requireActivity(), newsViewModel, newsViewModel.newsHeadlines))
		}
		binding.rvHome.adapter = adapter
	}
}