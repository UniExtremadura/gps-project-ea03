package com.example.familycoin.gridView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.familycoin.R
import com.example.familycoin.database.Database
import kotlinx.coroutines.launch

class UserTaskAdapter(var context:Context, var taskList: ArrayList<UserTaskItem>, private val navController: NavController) : BaseAdapter() {

    override fun getCount(): Int {
        return taskList.size
    }

    override fun getItem(position: Int): Any {
        return taskList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View {

        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.grid_user_task_item, parent, false)
        }
        val taskItem = this.getItem(position) as UserTaskItem
        val taskName = view!!.findViewById<TextView>(R.id.gridItemName)
        val taskImage = view.findViewById<ImageView>(R.id.gridItemImage)

        taskName.text = taskItem.name
        taskImage.setImageResource(taskItem.image!!)


        view.setOnClickListener(){
            val sharedPref = context?.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE)
            val editor = sharedPref?.edit()
            editor?.putString("confirmTask", taskItem.name)
            editor?.apply()


            navController.navigate(R.id.userConfirmRewardItemDescriptionFragment)
        }
        return view
    }

}