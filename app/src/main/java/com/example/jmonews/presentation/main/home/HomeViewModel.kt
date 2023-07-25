package com.example.jmonews.presentation.main.home

import androidx.lifecycle.ViewModel
import com.example.jmonews.data.model.home.OtherService
import com.example.jmonews.data.model.home.OtherServiceItem
import com.example.jmonews.data.model.home.ProgramService
import com.example.jmonews.data.model.home.ProgramServiceItem

class HomeViewModel : ViewModel() {
	fun getProgramServiceData(): List<ProgramServiceItem> = ProgramService.data
	fun getOtherServiceData(): List<OtherServiceItem> = OtherService.data
}