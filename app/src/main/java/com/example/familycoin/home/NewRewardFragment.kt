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
import com.example.familycoin.databinding.FragmentNewRewardBinding
import com.example.familycoin.databinding.FragmentNewTaskBinding
import com.example.familycoin.model.Reward
import com.example.familycoin.model.Task
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewRewardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewRewardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentNewRewardBinding
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
        binding = FragmentNewRewardBinding.inflate(inflater, container, false)
        val view = binding.root

        val myButton = binding.btnAddNewReward

        val nameText = binding.nameRewardEditText
        val descriptionText = binding.descriptionRewardEditText
        val costText = binding.costRewardEditText

        myButton.setOnClickListener {
            val nameEditText = nameText.text.toString()
            val descriptionEditText = descriptionText.text.toString()
            val costEditText = costText.text.toString().toInt()
            lifecycleScope.launch {
                val sharedPref = context?.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE)
                val valorString = sharedPref?.getString("username", "default")
                val user = db.userDao().findByName(valorString!!)
                if (user != null) {
                    if (user.familyCoinId != null){
                        val newReward = Reward(rewardName = nameEditText, description = descriptionEditText, cost = costEditText, familyCoinId = user.familyCoinId!!, assignedUserName = null, imageUrl = R.drawable.reward)
                        db.rewardDao().insert(newReward)
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
         * @return A new instance of fragment NewRewardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewRewardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}