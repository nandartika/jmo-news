package com.example.jmonews.data.model.home

import com.example.jmonews.R

data class ProgramServiceItem(
	val title: String, val caption: String, val iconId: Int
)

object ProgramService {
	val data = listOf(
		ProgramServiceItem("Jaminan Hari Tua", "Anda sudah terdaftar di layanan ini", R.drawable.ic_money_24),
		ProgramServiceItem("Jaminan Kecelakaan Kerja", "Anda sudah terdaftar di layanan ini", R.drawable.ic_people_24)
	)
}