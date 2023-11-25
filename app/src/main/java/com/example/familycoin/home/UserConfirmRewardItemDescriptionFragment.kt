package com.example.familycoin.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.familycoin.R
import com.example.familycoin.database.Database
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TaskItemDescriptionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserConfirmRewardItemDescriptionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var db: Database
    private lateinit var taskDescription: TextView
    private lateinit var taskPrice: TextView
    private lateinit var taskName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        db = Database.getInstance(requireContext())!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_confirm_reward_item_description, container, false)
        taskDescription = view.findViewById(R.id.taskDescriptionText)
        taskPrice = view.findViewById(R.id.taskValue)
        taskName = view.findViewById(R.id.taskName)

        lifecycleScope.launch {
            setDataList()
        }
        val myButton = view.findViewById<TextView>(R.id.confirmTaskRewardButton)

        myButton.setOnClickListener{

            lifecycleScope.launch {
                val sharedPref = context?.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE)
                val valorString = sharedPref?.getString("username", "default")
                val user2 = db.userDao().findByName(valorString!!)
                if (user2.type == 1) {
                    val task = db.taskDao().findByName(taskName.text.toString())
                    val username = task.assignedUserName
                    db.taskDao().delete(task)
                    val user = db.userDao().findByName(username!!)
                    user.coins += task.reward
                    db.userDao().update(user)
                    HomeActivity.start(requireContext(), user2)
                }
                else{
                    Toast.makeText(requireContext(), "Only parents can confirm reward", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return view
    }

    private suspend fun setDataList() {
        val sharedPref = context?.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE)
        val valorString = sharedPref?.getString("confirmTask", "default")
        val taskDetailed = db.taskDao().findByName(valorString!!)

        this.taskName.text = taskDetailed.taskName
        this.taskDescription.text = taskDetailed.description
        this.taskPrice.text = taskDetailed.reward.toString()

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TaskItemDescriptionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserConfirmRewardItemDescriptionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}