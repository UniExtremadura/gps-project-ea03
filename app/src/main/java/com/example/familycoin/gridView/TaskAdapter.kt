package com.example.familycoin.gridView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.familycoin.R

class TaskAdapter(var context:Context, var taskList: ArrayList<TaskItem>) : BaseAdapter() {
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
            view = LayoutInflater.from(context).inflate(R.layout.grid_item_list, parent, false)
        }
        val taskItem = this.getItem(position) as TaskItem
        val taskName = view!!.findViewById<TextView>(R.id.taskItemName)
        val taskImage = view.findViewById<ImageView>(R.id.taskItemImage)
        taskName.text = taskItem.name
        taskImage.setImageResource(taskItem.image!!)
        return view
    }

}