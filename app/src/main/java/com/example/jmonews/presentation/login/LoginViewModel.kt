package com.example.jmonews.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {
	private val validEmail = "kartika@gmail.com"
	private val validPassword = "K@rtikaA"

	private val _emailError = MutableLiveData<String>()
	val emailError: LiveData<String> = _emailError

	private val _passwordError = MutableLiveData<String>()
	val passwordError: LiveData<String> = _passwordError

	fun validateEmail(email: String) {
		val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

		if (email.isEmpty()) {
			_emailError.value = "Harap isi email Anda"
		} else if(!emailRegex.matches(email)) {
			_emailError.value = "Email tidak valid"
		} else {
			_emailError.value = null
		}
	}

	fun validatePassword(password: String) {
		if (password.isEmpty()) {
			_passwordError.value = "Harap isi password Anda"
		} else {
			_passwordError.value = null
		}
	}

	fun login(username: String, password: String): Boolean {
		if (username == validEmail && password == validPassword) {
			return true
		}
		return false
	}
}