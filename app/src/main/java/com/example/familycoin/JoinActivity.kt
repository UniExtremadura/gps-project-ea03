package com.example.familycoin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.familycoin.database.Database

import com.example.familycoin.databinding.ActivityJoinBinding
import com.example.familycoin.model.User
import com.example.familycoin.utils.CredentialCheck
import kotlinx.coroutines.launch

class JoinActivity : AppCompatActivity() {

    private lateinit var db: Database
    private lateinit var binding: ActivityJoinBinding

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
                    var typeSwitch = 0
                    if (switch2.isChecked) typeSwitch = 1
                    else typeSwitch = 2
                    val user = User(
                        etUsername.text.toString(),
                        etPassword.text.toString(),
                        typeSwitch,
                        null,
                        0
                    )
                    if(db.userDao().findByName(user.name) != null) {
                        notifyInvalidCredentials("Username already exists")
                    }
                    else {
                        db.userDao().insert(user)
                        navigateBackWithResult(
                            User(
                                etUsername.text.toString(),
                                etPassword.text.toString(),
                                typeSwitch,
                                null,
                                0
                            )
                        )
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