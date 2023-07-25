package com.example.jmonews.presentation.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.jmonews.databinding.FragmentHomeBinding
import com.example.jmonews.presentation.main.home.section.OtherServiceSection
import com.example.jmonews.presentation.main.home.section.ProgramServiceSection
import com.xwray.groupie.GroupieAdapter

class HomeFragment : Fragment() {
	private var _binding: FragmentHomeBinding? = null
	private val binding get() = _binding!!


	private lateinit var homeViewModel: HomeViewModel
	private var adapter: GroupieAdapter? = null

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentHomeBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setupViewModel()
		setupView()
	}

	private fun setupViewModel() {
		homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
	}

	private fun setupView() {
		adapter = GroupieAdapter()
		adapter?.apply {
			clear()
			val programServiceData = homeViewModel.getProgramServiceData()
			add(ProgramServiceSection(requireActivity(), programServiceData))

			val otherServiceData = homeViewModel.getOtherServiceData()
			add(OtherServiceSection(otherServiceData))
//			add(InformationSection())
		}
		binding.rvHome.adapter = adapter
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}