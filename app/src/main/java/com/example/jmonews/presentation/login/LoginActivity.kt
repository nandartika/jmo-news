package com.example.jmonews.presentation.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.jmonews.databinding.ActivityLoginBinding
import com.example.jmonews.presentation.main.MainActivity

class LoginActivity : AppCompatActivity() {
	private lateinit var binding: ActivityLoginBinding
	private lateinit var loginViewModel: LoginViewModel
	private var isEmailValid = false
	private var isPasswordValid = false

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityLoginBinding.inflate(layoutInflater)
		setContentView(binding.root)

		loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
		setupView()
		setupVariableObserve()
	}

	private fun setupVariableObserve() {
		loginViewModel.emailError.observe(this) { error ->
			binding.ilEmail.error = error
			isEmailValid = error == null
		}

		loginViewModel.passwordError.observe(this) { error ->
			binding.ilPassword.error = error
			isPasswordValid = error == null
		}
	}

	private fun setupView() {
		binding.etEmail.addTextChangedListener { s ->
			val enteredText = s?.toString()
			enteredText?.let { loginViewModel.validateEmail(it) }
		}

		binding.etPassword.addTextChangedListener { s ->
			val enteredText = s?.toString()
			enteredText?.let { loginViewModel.validatePassword(it) }
		}

		binding.btnLogin.setOnClickListener {
			val username = binding.etEmail.text.toString()
			val password = binding.etPassword.text.toString()

			val intent = Intent(this, MainActivity::class.java)
			startActivity(intent)
			finish()

//			if (isEmailValid && isPasswordValid) {
//				if (loginViewModel.login(username, password)) {
//					val intent = Intent(this, MainActivity::class.java)
//					startActivity(intent)
//					finish()
//				} else {
//					// Handle login failure
//					Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
//					// TODO : Show Bottom Fragment
//				}
//			} else {
//				// Show error messages for invalid input
//				loginViewModel.validateEmail(username)
//				loginViewModel.validatePassword(password)
//			}
		}
	}
}