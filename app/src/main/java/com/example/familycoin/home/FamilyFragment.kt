package com.example.familycoin.home
import com.example.familycoin.recyclerView.FamilyAdapter
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
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.familycoin.R
import com.example.familycoin.database.Database
import com.example.familycoin.model.User
import com.example.familycoin.recyclerView.FamilyItem
import com.example.familycoin.viewModel.FamilyViewModel
import com.example.familycoin.viewModel.HomeViewModel
import kotlinx.coroutines.launch

class FamilyFragment : Fragment(){

    private val viewModel: FamilyViewModel by viewModels { FamilyViewModel.Factory }
    private val homeViewModel: HomeViewModel by activityViewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FamilyAdapter
    private lateinit var familyNameTextView: TextView
    private lateinit var familyCodeTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_family, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        familyNameTextView = view.findViewById(R.id.textViewFamilyName)
        familyCodeTextView = view.findViewById(R.id.codeTextView)
        lifecycleScope.launch {
            setDataList()
        }

        return view
    }


    private suspend fun setDataList() {


        familyNameTextView.text = viewModel.getFamilyName(homeViewModel.userSession!!)
        familyCodeTextView.text = "Family Code: " + viewModel.getFamilyCode(homeViewModel.userSession!!)


        val navController = Navigation.findNavController(requireView())
        adapter = FamilyAdapter(this.requireContext(),viewModel.getListFamilyItem(homeViewModel.userSession!!), navController)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }
}