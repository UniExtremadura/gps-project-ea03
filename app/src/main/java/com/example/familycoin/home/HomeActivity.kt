package com.example.familycoin.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.familycoin.LoginActivity
import com.example.familycoin.R
import com.example.familycoin.database.Database

import com.example.familycoin.databinding.ActivityHomeBinding
import com.example.familycoin.model.User
import kotlinx.coroutines.launch


class HomeActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding
    private lateinit var db: Database

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    companion object {
        const val USER_INFO = "USER_INFO"
        fun start(
            context: Context,
            user: User,
        ) {
            val intent = Intent(context, HomeActivity::class.java).apply {
                putExtra(USER_INFO, user)
            }
            context.startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Database.getInstance(applicationContext)!!

        val user = intent.getSerializableExtra(USER_INFO) as User
        lifecycleScope.launch {
            val user1 = db.userDao().findByName(user.name) as User
            val miTextView = findViewById<TextView>(R.id.coins)
            miTextView.text = user1.coins.toString()
            setUpUI(user1)
        }
        setUpListeners()
    }

    fun setUpUI(user: User) {

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigation.setupWithNavController(navController)


        appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.tasksFragment,
                    R.id.shopFragment,
                    R.id.familyFragment)
        )

        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.familyFragment -> {
                    val destinationId = if (user.familyCoinId != null) R.id.familyFragment else R.id.noFamilyFragment
                    navController.navigate(destinationId)
                    true
                }
                else -> {
                    navController.navigate(item.itemId)
                    true
                }

            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings  -> {
        // User chooses the "Settings" item. Show the app settings UI.
        Toast.makeText(this, "Settings option", Toast.LENGTH_SHORT).show()
            true
        }
        R.id.action_logout -> {
            val sharedPreferences = getSharedPreferences("CurrentUser", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

            true
        }
        R.id.action_profile -> {
            Toast.makeText(this, "Profile option", Toast.LENGTH_SHORT).show()
            true
        }
        else -> {
        // The user's action isn't recognized.
        // Invoke the superclass to handle it.
        super.onOptionsItemSelected(item)
        }
    }


    fun setUpListeners() {
    }

}