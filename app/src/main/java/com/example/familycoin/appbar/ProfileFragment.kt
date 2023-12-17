package com.example.familycoin.appbar

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.familycoin.R
import com.example.familycoin.database.Database
import com.example.familycoin.viewModel.FamilyViewModel
import com.example.familycoin.viewModel.HomeViewModel
import com.example.familycoin.viewModel.ProfileViewModel
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var db: Database
    private lateinit var titleFullName: TextView
    private lateinit var fullName: TextView
    private lateinit var familyGroup: TextView
    private lateinit var coins: TextView
    private val viewModel: ProfileViewModel by viewModels { ProfileViewModel.Factory }
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
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        titleFullName = view.findViewById(R.id.titleNameTextView)
        fullName = view.findViewById(R.id.emptyFullNameTextView)
        familyGroup = view.findViewById(R.id.emptyFamilyGroupTextView)
        coins = view.findViewById(R.id.emptyCoinsTextView)

        lifecycleScope.launch {
            familyGroup.text = viewModel.getFamilyGroup(homeViewModel.userSession!!)
            titleFullName.text = homeViewModel.userSession!!.name
            fullName.text = homeViewModel.userSession!!.name
            coins.text = viewModel.getUserCoins(homeViewModel.userSession!!)
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
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}