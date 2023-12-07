package com.example.familycoin.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.familycoin.R
import com.example.familycoin.gridView.TaskAdapter
import com.example.familycoin.gridView.TaskItem
import androidx.navigation.fragment.findNavController
import com.example.familycoin.database.Database
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TasksFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TasksFragment : Fragment() , AdapterView.OnItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var gridView: GridView
    private lateinit var taskList: ArrayList<TaskItem>
    private lateinit var adapter:TaskAdapter
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
        val arrayList: ArrayList<TaskItem> = ArrayList()

        val sharedPref = context?.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE)
        val valorString = sharedPref?.getString("username", "default")
        val taskUser = db.userDao().findByName(valorString.toString())
        if (taskUser.familyCoinId != null) {
            val taskListUser = db.taskDao().findByFamilyCoinId(taskUser.familyCoinId!!)

            if (taskListUser != null && taskListUser.isNotEmpty()) {
                var assigned: Boolean
                for (task in taskListUser) {
                    assigned = false
                    if(task.assignedUserName != null){
                        assigned = true
                    }

                    arrayList.add(TaskItem(task.taskName, R.drawable.baseline_task_24, assigned))
                }
                adapter = TaskAdapter(requireContext(), arrayList)
                gridView.adapter = adapter
                gridView.onItemClickListener = this
            }
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tasks, container, false)
        gridView = view.findViewById(R.id.gridViewTasks)

        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.visibility = View.VISIBLE

        lifecycleScope.launch {
            setDataList()
        }



        val btnNewTask = view.findViewById<View>(R.id.addTaskButton)

        lifecycleScope.launch {
            val sharedPref = context?.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE)
            val valorString = sharedPref?.getString("username", "default")
            val user = db.userDao().findByName(valorString.toString())
            if(user != null){
                    if(user.type == 1){
                        btnNewTask.visibility = View.VISIBLE
                    }
                    else{
                        btnNewTask.visibility = View.GONE
                    }
                }
            }

        btnNewTask.setOnClickListener {
            lifecycleScope.launch {
                val sharedPref = context?.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE)
                val valorString = sharedPref?.getString("username", "default")
                val user = db.userDao().findByName(valorString.toString())
                if(user != null){
                    if(user.familyCoinId == null){
                        Toast.makeText(requireContext(), "You are not in a family", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        findNavController().navigate(R.id.newTaskFragment)
                    }
                }

            }

        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gridView = view.findViewById<GridView>(R.id.gridViewTasks)
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)

        gridView.setOnScrollListener(object : AbsListView.OnScrollListener {
            private var lastVisibleItem = 0
            private var lastFirstVisibleItem = 0

            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
                // No es necesario implementar nada aquí, pero se podría si necesitas cierta lógica al cambiar de estado
            }

            override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {

                if (firstVisibleItem > lastFirstVisibleItem && bottomNavigationView.visibility == View.VISIBLE) {
                    // Si el usuario está deslizando hacia abajo y la barra de navegación está visible, ocúltala
                    bottomNavigationView.visibility = View.GONE
                } else if (firstVisibleItem < lastFirstVisibleItem && bottomNavigationView.visibility != View.VISIBLE) {
                    // Si el usuario está deslizando hacia arriba y la barra de navegación no está visible, muéstrala
                    bottomNavigationView.visibility = View.VISIBLE
                }
                lastFirstVisibleItem = firstVisibleItem

            }
        })
    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DiscoverFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TasksFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
        val taskItem: TaskItem = taskList.get(position)
        Toast.makeText(requireContext(), taskItem.name, Toast.LENGTH_SHORT).show()
    }
}