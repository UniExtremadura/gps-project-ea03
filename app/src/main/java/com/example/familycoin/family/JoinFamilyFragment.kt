package com.example.familycoin.family

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.familycoin.R
import com.example.familycoin.database.Database
import com.example.familycoin.databinding.FragmentCreateFamilyBinding
import com.example.familycoin.databinding.FragmentJoinFamilyBinding
import com.example.familycoin.home.HomeActivity
import com.example.familycoin.model.Family
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [JoinFamilyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class JoinFamilyFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentJoinFamilyBinding
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
        binding = FragmentJoinFamilyBinding.inflate(inflater, container, false)
        val view = binding.root

        val myButton = binding.btnJoinFamily

        val editText = binding.joinFamilyName

        myButton.setOnClickListener {
            val textoEditText = editText.text.toString().toLong()
            lifecycleScope.launch {
                val family = db?.familyDao()?.findById(textoEditText)
                if (family != null) {
                    val sharedPref = context?.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE)
                    val valorString = sharedPref?.getString("username", "default")
                    var userUpdate = db.userDao().findByName(valorString!!)
                    userUpdate.familyCoinId = family?.familyCoinId
                    if (userUpdate != null) {
                        db.userDao().update(userUpdate)
                    }
                    HomeActivity.start(requireContext(), userUpdate)
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
         * @return A new instance of fragment JoinFamilyFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            JoinFamilyFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}