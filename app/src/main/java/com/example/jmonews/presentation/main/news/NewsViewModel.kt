package com.example.jmonews.presentation.main.news

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.jmonews.data.model.news.APIResponse
import com.example.jmonews.data.util.Resource
import com.example.jmonews.domain.usecase.GetNewsHeadlinesUseCase
import com.example.jmonews.domain.usecase.GetNewsTopHeadlineUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(
	private val app: Application,
	private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
	private val getNewsTopHeadlineUseCase: GetNewsTopHeadlineUseCase
) : AndroidViewModel(app) {
	private val _newsHeadlines: MutableLiveData<Resource<APIResponse>> = MutableLiveData()
	val newsHeadlines: LiveData<Resource<APIResponse>> = _newsHeadlines

	val newsTopHeadlines: MutableLiveData<Resource<APIResponse>> = MutableLiveData()

	fun getNewsHeadlines(page: Int) = viewModelScope.launch(Dispatchers.IO) {
		try {
			if (isNetworkAvailable(app)) {
				_newsHeadlines.postValue(Resource.Loading())
				val apiResult = getNewsHeadlinesUseCase.execute(page)
				_newsHeadlines.postValue(apiResult)
			} else {
				_newsHeadlines.postValue(Resource.Error("Network is unavailable"))
			}
		} catch (e: Exception) {
			_newsHeadlines.postValue(Resource.Error(e.message.toString()))
		}
	}

	fun getNewsTopHeadline() = viewModelScope.launch(Dispatchers.IO) {
		try {
			if (isNetworkAvailable(app)) {
				newsTopHeadlines.postValue(Resource.Loading())
				val apiResult = getNewsTopHeadlineUseCase.execute()
				newsTopHeadlines.postValue(apiResult)
			} else {
				newsTopHeadlines.postValue(Resource.Error("Network is unavailable"))
			}
		} catch (e: Exception) {
			newsTopHeadlines.postValue(Resource.Error(e.message.toString()))
		}
	}

	private fun isNetworkAvailable(context: Context?): Boolean {
		if (context == null) return false

		val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			val networkCapabilities = connectivityManager.activeNetwork ?: return false
			val capabilities = connectivityManager.getNetworkCapabilities(networkCapabilities)
			return capabilities?.run {
				hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
						hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
						hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
			} ?: false
		} else {
			val activeNetworkInfo = connectivityManager.activeNetworkInfo
			return activeNetworkInfo?.isConnected ?: false
		}
	}
}