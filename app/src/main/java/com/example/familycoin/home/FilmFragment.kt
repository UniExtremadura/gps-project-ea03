package com.example.familycoin.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.familycoin.R
import com.example.familycoin.repository.RepositoryApi
import com.example.familycoin.gridView.MovieItem
import com.example.familycoin.gridView.FilmAdapter
import com.example.familycoin.gridView.ShopItem
import com.example.familycoin.viewModel.FilmViewModel
import com.example.familycoin.viewModel.HomeViewModel
import com.example.familycoin.viewModel.NewTaskViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShopFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FilmFragment : Fragment() , AdapterView.OnItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var gridView: GridView
    private lateinit var shopList: ArrayList<ShopItem>
    private lateinit var adapter:FilmAdapter
    private val viewModel: FilmViewModel by viewModels { FilmViewModel.Factory }
    private val homeViewModel: HomeViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        //shopList = setDataList()
    }

    private fun setDataList() : ArrayList<ShopItem>{
        val arrayList: ArrayList<ShopItem> = ArrayList()

        arrayList.add(ShopItem("Reward 1", R.drawable.reward))
        arrayList.add(ShopItem("Reward 2", R.drawable.reward))
        arrayList.add(ShopItem("Reward 3", R.drawable.reward))
        arrayList.add(ShopItem("Reward 4", R.drawable.reward))
        arrayList.add(ShopItem("Reward 5", R.drawable.reward))

        return arrayList
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_film, container, false)
        gridView = view.findViewById(R.id.gridViewFilm)

        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                // Cambia a Main para actualizar la interfaz de usuario
                withContext(Dispatchers.Main) {
                    // Crea la lista de MovieItem a partir de la lista de Film
                    val movieItemList = viewModel.getFilms().map { film ->
                        MovieItem(title = film.filmTitle, posterUrl = film.filmPoster, year = film.filmYear, plot = film.filmPlot, cost = 100)
                    }

                    // Actualiza el adaptador con la nueva lista de películas
                    val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                    adapter = FilmAdapter(requireContext(), movieItemList, navController)
                    gridView.adapter = adapter
                }
            } catch (e: Exception) {
                // Maneja errores
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Error al obtener datos de la API: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gridView = view.findViewById<GridView>(R.id.gridViewFilm)
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)

        gridView.setOnScrollListener(object : AbsListView.OnScrollListener {
            private var lastVisibleItem = 0
            private var lastFirstVisibleItem = 0

            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
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
            ShopFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
        val ShopItem: ShopItem = shopList.get(position)
        Toast.makeText(requireContext(), ShopItem.name, Toast.LENGTH_SHORT).show()
    }
}