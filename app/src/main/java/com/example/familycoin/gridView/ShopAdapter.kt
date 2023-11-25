package com.example.familycoin.gridView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.familycoin.R

class ShopAdapter(var context:Context, var movieList: List<MovieItem>) : BaseAdapter() {
    override fun getCount(): Int {
        return movieList.size
    }

    override fun getItem(position: Int): Any {
        return movieList[position]
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
        val movieItem = getItem(position) as MovieItem
        val movieTitle = view!!.findViewById<TextView>(R.id.gridItemName)
        val movieImage = view.findViewById<ImageView>(R.id.gridItemImage)

        movieTitle.text = movieItem.title

        // Carga la imagen desde la URL usando Glide
        Glide.with(context)
            .load(movieItem.posterUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .into(movieImage)

        return view
    }

}