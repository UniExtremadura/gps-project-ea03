package com.example.familycoin.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.familycoin.R

import com.example.familycoin.databinding.ActivityHomeBinding
import com.example.familycoin.model.User

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    private lateinit var tasksFragment: TasksFragment
    private lateinit var shopFragment: ShopFragment
    private lateinit var familyFragment: FamilyFragment

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

        val user = intent.getSerializableExtra(USER_INFO) as User

        setUpUI(user)
        setUpListeners()
    }

    fun setUpUI(user: User) {
        tasksFragment = TasksFragment()
        shopFragment = ShopFragment()
        familyFragment = FamilyFragment()

        //TODO set discoverFragment as default fragment
        setCurrentFragment(tasksFragment)

    }

    fun setUpListeners() {
        //TODO set listeners for bottom navigation bar
        with(binding) {
            bottomNavigation.setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.action_tasks -> setCurrentFragment(tasksFragment)
                    R.id.action_shop -> setCurrentFragment(shopFragment)
                    R.id.action_family -> setCurrentFragment(familyFragment)
                }
                true
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()
        }


}