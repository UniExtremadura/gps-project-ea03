package com.example.familycoin.family

import android.content.Context
import android.content.Intent
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
import com.example.familycoin.databinding.FragmentCreateFamilyBinding
import com.example.familycoin.home.HomeActivity
import com.example.familycoin.model.Family
import com.example.familycoin.model.Reward
import com.example.familycoin.model.User
import com.example.familycoin.viewModel.CreateFamilyViewModel
import com.example.familycoin.viewModel.HomeViewModel
import com.example.familycoin.viewModel.ProfileViewModel
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CreateFamilyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateFamilyFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentCreateFamilyBinding
    private val viewModel: CreateFamilyViewModel by viewModels { CreateFamilyViewModel.Factory }
    private val homeViewModel: HomeViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateFamilyBinding.inflate(inflater, container, false)
        val view = binding.root

        // Obtener referencia al botÃ³n desde el enlace
        val myButton = binding.btnCreateFamily

        val editText = binding.editTextText

        myButton.setOnClickListener {
            lifecycleScope.launch {
                try {
                    viewModel.createFamily(editText.text.toString())
                    viewModel.insertInitialsRewards(editText.text.toString())
                    HomeActivity.start(requireContext(), viewModel.updateUserFamilyCoinId(homeViewModel.userSession!!, editText.text.toString()))
                }
                catch (e: Exception) {
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
         * @return A new instance of fragment CreateFamilyFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreateFamilyFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}