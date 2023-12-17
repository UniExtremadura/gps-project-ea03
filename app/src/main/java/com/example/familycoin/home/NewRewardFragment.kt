package com.example.familycoin.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.familycoin.R
import com.example.familycoin.database.Database
import com.example.familycoin.databinding.FragmentNewRewardBinding
import com.example.familycoin.databinding.FragmentNewTaskBinding
import com.example.familycoin.model.Reward
import com.example.familycoin.model.Task
import com.example.familycoin.viewModel.CreateFamilyViewModel
import com.example.familycoin.viewModel.HomeViewModel
import com.example.familycoin.viewModel.NewRewardViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
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
    private val viewModel: NewRewardViewModel by viewModels { NewRewardViewModel.Factory }
    private val homeViewModel: HomeViewModel by activityViewModels()

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

        val buttonAddNewReward = binding.btnAddNewReward

        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.visibility = View.VISIBLE

        val nameText = binding.nameRewardEditText
        val descriptionText = binding.descriptionRewardEditText
        val costText = binding.costRewardEditText

        buttonAddNewReward.setOnClickListener {
                lifecycleScope.launch {
                    try {
                        viewModel.createReward(
                            nameText.text.toString(),
                            null,
                            homeViewModel.getUserFamilyCoinId()!!,
                            costText.text.toString().toIntOrNull(),
                            descriptionText.text.toString()
                        )
                        HomeActivity.start(requireContext(), homeViewModel.userSession!!)
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
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