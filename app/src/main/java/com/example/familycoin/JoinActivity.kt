package com.example.familycoin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.familycoin.database.Database

import com.example.familycoin.databinding.ActivityJoinBinding
import com.example.familycoin.model.User
import com.example.familycoin.utils.CredentialCheck
import com.example.familycoin.viewModel.JoinViewModel
import kotlinx.coroutines.launch

class JoinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJoinBinding
    private val viewModel: JoinViewModel by viewModels { JoinViewModel.Factory  }

    companion object {

        const val USERNAME = "JOIN_USERNAME"
        const val PASS = "JOIN_PASS"
        fun start(
            context: Context,
            responseLauncher: ActivityResultLauncher<Intent>
        ) {
            val intent = Intent(context, JoinActivity::class.java)
            responseLauncher.launch(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinBinding.inflate(layoutInflater)
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
            btRegister.setOnClickListener {
                join()
            }
        }
    }

    private fun join() {
        with(binding) {
            val check = CredentialCheck.join(
                etUsername.text.toString(),
                etPassword.text.toString(),
                etRepassword.text.toString()
            )

            if (check.fail) {
                notifyInvalidCredentials(check.msg)
            } else {
                lifecycleScope.launch {
                    val typeSwitch : Int = if (switch2.isChecked) 2
                    else 1
                    try {
                        val user = viewModel.register(etUsername.text.toString(), etPassword.text.toString(), typeSwitch)
                        navigateBackWithResult(user)
                    } catch (e: Exception) {
                        notifyInvalidCredentials(e.message ?: "Join failed")
                    }
                }
            }
        }
    }

    private fun navigateBackWithResult(user: User) {
        val intent = Intent().apply {
            putExtra(USERNAME,user.name)
            putExtra(PASS,user.password)
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun notifyInvalidCredentials(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}