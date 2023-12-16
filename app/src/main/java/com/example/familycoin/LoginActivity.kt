package com.example.familycoin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.familycoin.database.Database

import com.example.familycoin.databinding.ActivityLoginBinding
import com.example.familycoin.model.User
import com.example.familycoin.utils.CredentialCheck
import com.example.familycoin.home.HomeActivity
import com.example.familycoin.viewModel.LoginViewModel
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels { LoginViewModel.Factory }

    private val responseLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                with(result.data) {
                    val name = this?.getStringExtra(JoinActivity.USERNAME).orEmpty()
                    val password = this?.getStringExtra(JoinActivity.PASS).orEmpty()

                    with(binding) {
                        etPassword.setText(password)
                        etUsername.setText(name)
                    }

                    Toast.makeText(
                        this@LoginActivity,
                        "New user ($name/$password) created",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //view binding and set content view
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //views initialization and listeners
        setUpUI()
        setUpListeners()
    }

    private fun setUpUI() {
        //get attributes from xml using binding
    }

    private fun setUpListeners() {
        with(binding) {

            btLogin.setOnClickListener {
                checkLogin()
            }

            btRegister.setOnClickListener {
                navigateToJoin()
            }
        }
    }

    private fun checkLogin() {
        val check = CredentialCheck.login(
            binding.etUsername.text.toString(),
            binding.etPassword.text.toString()
        )

        if (!check.fail) {
            lifecycleScope.launch {
                try {
                    val user = viewModel.login(binding.etUsername.text.toString(), binding.etPassword.text.toString())
                    val sharedPreferences = getSharedPreferences("CurrentUser", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.clear()

                    editor.putString("username", user.name)
                    editor.apply()
                    navigateToHomeActivity(user, "Login successful")
                } catch (e: Exception) {
                    notifyInvalidCredentials(e.message  ?: "Login failed" )
                }
            }
        }
         else {
            notifyInvalidCredentials(check.msg)
        }
    }

    private fun navigateToHomeActivity(user: User, msg: String) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra(HomeActivity.USER_INFO, user)
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        startActivity(intent)
        HomeActivity.start(this, user)
    }

    private fun navigateToJoin() {
        val intent = Intent(this, JoinActivity::class.java)
        startActivity(intent)
    }

    private fun notifyInvalidCredentials(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}