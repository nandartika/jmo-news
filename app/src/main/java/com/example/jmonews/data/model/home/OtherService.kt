package com.example.jmonews.data.model.home

import com.example.jmonews.R

data class OtherServiceItem(
	val title: String, val iconId: Int
)

object OtherService {
	val data = listOf(
		OtherServiceItem("Info Program", 1),
		OtherServiceItem("Bayar/Autodebit", 2),
		OtherServiceItem("Daftar BPU", 2),
		OtherServiceItem("Pengkinian Data", 2),
		OtherServiceItem("Kantor Cabang", 2),
		OtherServiceItem("Mitra Layanan", 2),
		OtherServiceItem("Pengaduan", 2),
		OtherServiceItem("Bantuan", 2),
		OtherServiceItem("Menu Lainnya", R.drawable.baseline_more_horiz_24)
	)
}