package com.example.jmonews.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.jmonews.R
import com.example.jmonews.data.model.news.Article
import com.example.jmonews.databinding.ActivityMainBinding
import com.example.jmonews.presentation.main.digitalCard.DigitalCardFragment
import com.example.jmonews.presentation.main.home.HomeFragment
import com.example.jmonews.presentation.main.news.NewsFragment
import com.example.jmonews.presentation.main.profile.ProfileFragment

interface DataListener {
	fun onClickNewsHeadline(data: Article)
}

class MainActivity : AppCompatActivity(), DataListener {
	private lateinit var binding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		loadFragment(HomeFragment())
		binding.bottomNav.setOnItemSelectedListener {
			when (it.itemId) {
				R.id.home -> {
					loadFragment(HomeFragment())
					true
				}
				R.id.news -> {
					loadFragment(NewsFragment())
					true
				}
				R.id.digitalCard -> {
					loadFragment(DigitalCardFragment())
					true
				}
				R.id.profile -> {
					loadFragment(ProfileFragment())
					true
				}
				else -> false
			}
		}
	}

	private  fun loadFragment(fragment: Fragment){
		val transaction = supportFragmentManager.beginTransaction()
		transaction.replace(R.id.container,fragment)
		transaction.commit()
	}

	override fun onClickNewsHeadline(data: Article) {
		TODO("Not yet implemented")
	}
}