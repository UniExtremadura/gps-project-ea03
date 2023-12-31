package com.example.familycoin.api

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.familycoin.R
import com.example.familycoin.database.Database
import com.example.familycoin.home.HomeActivity
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFilmFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFilmFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var db: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        db = Database.getInstance(requireContext())!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el diseño del fragmento de detalles
        val view = inflater.inflate(R.layout.fragment_detail_film, container, false)

        // Obtener los datos de la película desde los argumentos
        val title = arguments?.getString("title")
        val year = arguments?.getString("year")
        val posterUrl = arguments?.getString("posterUrl")
        val plot = arguments?.getString("plot")
        val cost = arguments?.getString("cost")

        // Configurar las vistas con los datos de la película
        val movieTitle = view.findViewById<TextView>(R.id.textTitle)
        val movieYear = view.findViewById<TextView>(R.id.textYear)
        val moviePoster = view.findViewById<ImageView>(R.id.imagePoster)
        val moviePlot = view.findViewById<TextView>(R.id.textSynopsis)
        val movieCost = view.findViewById<TextView>(R.id.filmValue)

        movieTitle.text = title
        movieYear.text = year
        moviePlot.text = plot
        movieCost.text = cost

        // Cargar la imagen desde la URL usando Glide
        Glide.with(this)
            .load(posterUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .into(moviePoster)

        //Limpiar el bundle
        arguments?.clear()

        val myButton = view.findViewById<Button>(R.id.confirmFilmButton)

        lifecycleScope.launch {
            val sharedPref = context?.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE)
            val valorString = sharedPref?.getString("username", "default")
            val user = db.userDao().findByName(valorString.toString())
            if(user != null){
                if(user.type == 2){
                    myButton.visibility = View.VISIBLE
                }
                else{
                    myButton.visibility = View.GONE
                }
            }
        }

        myButton.setOnClickListener{
            lifecycleScope.launch {
                val sharedPref = context?.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE)
                val username = sharedPref?.getString("username", "default")
                val user = db.userDao().findByName(username!!)
                if (user.coins >= cost!!.toInt()) {
                    user.coins = user.coins!! - cost!!.toInt()
                    db.userDao().update(user)
                    HomeActivity.start(requireContext(), user)
                }
                else{
                    Toast.makeText(requireContext(), "You don't have enough coins", Toast.LENGTH_SHORT).show()
                }
            }
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
         * @return A new instance of fragment DetailFilmFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailFilmFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}