package com.example.familycoin.gridView

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.familycoin.R
import com.example.familycoin.api.DetailFilmFragment
import androidx.navigation.fragment.findNavController

class FilmAdapter(var context:Context, var movieList: List<MovieItem>, private val navController: NavController) : BaseAdapter() {


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
            view = LayoutInflater.from(context).inflate(R.layout.grid_user_task_item, parent, false)
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

        // Agregar un clic del elemento
        view.setOnClickListener {
            // Crear un bundle para pasar datos al fragmento de detalles
            val bundle = Bundle()
            bundle.putString("title", movieItem.title)
            bundle.putString("year", movieItem.year)  // Reemplaza con el año real
            bundle.putString("posterUrl", movieItem.posterUrl)
            bundle.putString("plot", movieItem.plot)  // Reemplaza con la trama real

            // Navegar al fragmento de detalles
            navController.navigate(R.id.filmDetailFragment, bundle)

        }



        return view
    }

}