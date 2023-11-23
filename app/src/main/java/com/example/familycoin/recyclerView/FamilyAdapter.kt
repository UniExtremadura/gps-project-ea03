package com.example.familycoin.recyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.familycoin.R
import com.example.familycoin.gridView.UserTaskItem


class FamilyAdapter(var context: Context, private val familyList: List<FamilyItem>, private val navController: NavController) : RecyclerView.Adapter<FamilyAdapter.FamilyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FamilyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_list, parent, false)
        return FamilyViewHolder(view)
    }

    private fun getCount(): Int {
        return familyList.size
    }
    private fun getItem(position: Int): Any {
        return familyList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: FamilyViewHolder, position: Int) {
        val familyItem = familyList[position]

        holder.imageView.setImageResource(familyItem.imageRes!!)
        holder.textView.text = familyItem.name

        holder.itemView.setOnClickListener {

            val sharedPref = context?.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE)
            val editor = sharedPref?.edit()
            editor?.putString("assignedUserName",familyItem.name)
            editor?.apply()

            navController.navigate(R.id.userTasksFragment)
        }

    }

    override fun getItemCount(): Int {
        return familyList.size
    }

    class FamilyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.recyclerItemImage)
        val textView: TextView = itemView.findViewById(R.id.recyclerItemName)
    }
}