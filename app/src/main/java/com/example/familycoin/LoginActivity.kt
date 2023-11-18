package com.example.familycoin

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.example.familycoin.database.Database

import com.example.familycoin.databinding.ActivityLoginBinding
import com.example.familycoin.model.User
import com.example.familycoin.utils.CredentialCheck
import com.example.familycoin.home.HomeActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var db: Database
    private lateinit var binding: ActivityLoginBinding

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

        db = Database.getInstance(applicationContext)!!

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
                val user = db?.userDao()?.findByName(binding.etUsername.text.toString())

                if (user != null) {
                    val passwordCheck = CredentialCheck.passwordOk(
                        binding.etPassword.text.toString(),
                        user.password
                    )

                    if (check.fail) {
                        notifyInvalidCredentials(check.msg)
                    } else {
                        navigateToHomeActivity(user, check.msg)
                    }
                } else {
                    notifyInvalidCredentials("Invalid username")
                }
            }
        } else {
            notifyInvalidCredentials(check.msg)
        }
    }

    private fun navigateToHomeActivity(user: User, msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        HomeActivity.start(this, user)
    }

    private fun navigateToJoin() {
        val intent = Intent(this, JoinActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToWebsite() {
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://trakt.tv/"))
        startActivity(webIntent)
    }

    private fun notifyInvalidCredentials(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}