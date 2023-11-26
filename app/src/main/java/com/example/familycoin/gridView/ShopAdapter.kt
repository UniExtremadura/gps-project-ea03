package com.example.familycoin.gridView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import com.example.familycoin.R

class ShopAdapter(var context:Context, var shopList: ArrayList<ShopItem>) : BaseAdapter() {
    override fun getCount(): Int {
        return shopList.size
    }

    override fun getItem(position: Int): Any {
        return shopList[position]
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
        val shopItem = this.getItem(position) as ShopItem
        val shopName = view!!.findViewById<TextView>(R.id.gridItemName)
        val shopImage = view.findViewById<ImageView>(R.id.gridItemImage)
        shopName.text = shopItem.name
        shopImage.setImageResource(shopItem.image!!)

        view.setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.filmFragment)
        }

        return view
    }

}