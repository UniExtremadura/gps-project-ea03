package com.example.familycoin.home

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
import com.example.familycoin.viewModel.HomeViewModel
import com.example.familycoin.viewModel.TaskItemDescriptionViewModel
import com.example.familycoin.viewModel.TaskViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
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
class TaskItemDescriptionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var taskDescription: TextView
    private lateinit var taskPrice: TextView
    private lateinit var taskName: TextView
    private val viewModel: TaskItemDescriptionViewModel by viewModels { TaskItemDescriptionViewModel.Factory }
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
        val view = inflater.inflate(R.layout.fragment_task_item_description, container, false)
        taskDescription = view.findViewById(R.id.taskDescriptionText)
        taskPrice = view.findViewById(R.id.taskValue)
        taskName = view.findViewById(R.id.taskName)

        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.visibility = View.VISIBLE

        lifecycleScope.launch {
            setDataList()
        }
        val buttonAcceptTask = view.findViewById<TextView>(R.id.acceptTaskButton)

        if(homeViewModel.userSession!!.type == 2){
            buttonAcceptTask.visibility = View.VISIBLE
        }
        else{
            buttonAcceptTask.visibility = View.GONE
        }


        buttonAcceptTask.setOnClickListener{
            lifecycleScope.launch {
                viewModel.updateUserTasks(homeViewModel.userSession!!, taskName.text.toString())
                HomeActivity.start(requireContext(), homeViewModel.userSession!!)
            }
        }

        return view
    }

    private suspend fun setDataList() {
        val sharedPref = context?.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE)
        val valorString = sharedPref?.getString("taskItem", "default")
        val taskDetailed = viewModel.getTask(valorString!!)

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
            TaskItemDescriptionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}