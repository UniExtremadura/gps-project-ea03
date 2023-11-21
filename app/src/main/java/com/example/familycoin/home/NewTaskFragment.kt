package com.example.familycoin.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.familycoin.R
import com.example.familycoin.database.Database
import com.example.familycoin.databinding.FragmentJoinFamilyBinding
import com.example.familycoin.databinding.FragmentNewTaskBinding
import com.example.familycoin.model.Task
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewTaskFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewTaskFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentNewTaskBinding
    private lateinit var db: Database

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
        binding = FragmentNewTaskBinding.inflate(inflater, container, false)
        val view = binding.root

        val myButton = binding.btnAddNewTask

        val nameText = binding.nameTaskEditText
        val descriptionText = binding.descriptionTaskEditText
        val rewardText = binding.rewardTaskEditText

        myButton.setOnClickListener {
            val nameEditText = nameText.text.toString()
            val descriptionEditText = descriptionText.text.toString()
            val rewardEditText = rewardText.text.toString().toInt()
            lifecycleScope.launch {
                val sharedPref = context?.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE)
                val valorString = sharedPref?.getString("username", "default")
                val user = db?.userDao()?.findByName(valorString!!)
                if (user != null) {
                    if (user.familyCoinId != null){
                        val newTask = Task(taskName = nameEditText, description = descriptionEditText, reward = rewardEditText, familyCoinId = user.familyCoinId!!, assignedUserName = null)
                        db?.taskDao()?.insert(newTask)
                    }
                    HomeActivity.start(requireContext(), user)
                }

            }
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NewTaskFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewTaskFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}