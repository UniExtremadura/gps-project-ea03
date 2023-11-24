package com.example.familycoin.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.familycoin.R
import com.example.familycoin.database.Database
import com.example.familycoin.gridView.TaskAdapter
import com.example.familycoin.gridView.TaskItem
import com.example.familycoin.gridView.UserTaskAdapter
import com.example.familycoin.gridView.UserTaskItem
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserTasksFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserTasksFragment : Fragment(), AdapterView.OnItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var gridView: GridView
    private lateinit var taskList: ArrayList<UserTaskItem>
    private lateinit var adapter: UserTaskAdapter
    private lateinit var db: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        db = Database.getInstance(requireContext())!!
    }

    private suspend fun setDataList(){
        var arrayList: ArrayList<UserTaskItem> = ArrayList()

        val sharedPref = context?.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE)
        val valorString = sharedPref?.getString("assignedUserName", "default")
        val taskUser = db.userDao().findByName(valorString.toString())
        if (taskUser?.familyCoinId != null) {
            val taskListUser = db.taskDao().findByUsername(valorString!!)

            if (taskListUser != null && taskListUser.isNotEmpty()) {
                for (task in taskListUser) {
                    arrayList.add(UserTaskItem(task.taskName, R.drawable.baseline_task_24))
                }
                val navController = Navigation.findNavController(requireView())
                adapter = UserTaskAdapter(requireContext(), arrayList, navController)
                gridView?.adapter = adapter
                gridView?.onItemClickListener = this
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user_tasks, container, false)
        gridView = view.findViewById(R.id.gridView)


        lifecycleScope.launch {
            setDataList()
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
         * @return A new instance of fragment UserTasksFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserTasksFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
        var userTaskItem: UserTaskItem = taskList.get(position)
        Toast.makeText(requireContext(), userTaskItem.name, Toast.LENGTH_SHORT).show()
    }
}