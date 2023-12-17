package com.example.familycoin.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.familycoin.R
import com.example.familycoin.database.Database
import com.example.familycoin.viewModel.HomeViewModel
import com.example.familycoin.viewModel.RewardItemDetailViewModel
import com.example.familycoin.viewModel.TaskItemDescriptionViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RewardItemDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var db: Database
    private lateinit var rewardDescription: TextView
    private lateinit var rewardPrice: TextView
    private lateinit var rewardName: TextView
    private lateinit var rewardImage: ImageView
    private val viewModel: RewardItemDetailViewModel by viewModels { RewardItemDetailViewModel.Factory }
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
        val view = inflater.inflate(R.layout.fragment_reward_item_detail, container, false)
        rewardDescription = view.findViewById(R.id.rewardDescriptionText)
        rewardPrice = view.findViewById(R.id.rewardValue)
        rewardName = view.findViewById(R.id.rewardName)
        rewardImage = view.findViewById(R.id.rewardImage)

        lifecycleScope.launch {
            setDataList()
        }
        val myButton = view.findViewById<Button>(R.id.acceptRewardButton)

        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.visibility = View.VISIBLE

        if(homeViewModel.userSession!!.type == 2){
            myButton.visibility = View.VISIBLE
        }
        else{
            myButton.visibility = View.GONE
        }


        myButton.setOnClickListener{
            lifecycleScope.launch {
                viewModel.updateUserRewards(homeViewModel.userSession!!, rewardName.text.toString())
                HomeActivity.start(requireContext(), homeViewModel.userSession!!)
            }
        }

        return view
    }

    private suspend fun setDataList() {
        val sharedPref = context?.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE)
        val valorString = sharedPref?.getString("rewardItem", "default")
        val rewardDetailed = viewModel.getReward(valorString!!)

        this.rewardName.text = rewardDetailed.rewardName
        this.rewardDescription.text = rewardDetailed.description
        this.rewardPrice.text = rewardDetailed.cost.toString()
        this.rewardImage.setImageResource(rewardDetailed.imageUrl!!)

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
            TaskItemDescriptionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
