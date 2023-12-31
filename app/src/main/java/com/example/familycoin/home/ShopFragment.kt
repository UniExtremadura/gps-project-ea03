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
import androidx.navigation.fragment.findNavController
import com.example.familycoin.R
import com.example.familycoin.database.Database
import com.example.familycoin.gridView.FilmAdapter
import com.example.familycoin.gridView.ShopAdapter
import com.example.familycoin.gridView.ShopItem
import com.example.familycoin.gridView.TaskAdapter
import com.example.familycoin.gridView.TaskItem
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShopFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShopFragment : Fragment() , AdapterView.OnItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var gridView: GridView
    private lateinit var shopList: ArrayList<ShopItem>
    private lateinit var adapter: ShopAdapter
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
        val arrayList: ArrayList<ShopItem> = ArrayList()

        val sharedPref = context?.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE)
        val valorString = sharedPref?.getString("username", "default")
        val shopUser = db.userDao().findByName(valorString.toString())
        if (shopUser.familyCoinId != null) {
            val shopListUser = db.rewardDao().findByFamilyCoinId(shopUser.familyCoinId!!)
            if (shopListUser != null && shopListUser.isNotEmpty()) {
                for (shop in shopListUser) {
                    arrayList.add(ShopItem(shop.rewardName, shop.imageUrl))
                }
            }
        }
        adapter = ShopAdapter(requireContext(), arrayList)
        gridView.adapter = adapter
        gridView.onItemClickListener = this
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_shop, container, false)
        gridView = view.findViewById(R.id.gridView)

        lifecycleScope.launch {
            setDataList()
        }

        val btnNewReward = view.findViewById<View>(R.id.btnAddReward)

        lifecycleScope.launch {
            val sharedPref = context?.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE)
            val valorString = sharedPref?.getString("username", "default")
            val user = db.userDao().findByName(valorString.toString())
            if(user != null){
                if(user.type == 1){
                    btnNewReward.visibility = View.VISIBLE
                }
                else{
                    btnNewReward.visibility = View.GONE
                }
            }
        }

        btnNewReward.setOnClickListener {
            lifecycleScope.launch {
                val sharedPref = context?.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE)
                val valorString = sharedPref?.getString("username", "default")
                val user = db.userDao().findByName(valorString.toString())
                if(user != null){
                    if(user.familyCoinId == null){
                        Toast.makeText(requireContext(), "You are not in a family", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        findNavController().navigate(R.id.newRewardFragment)
                    }
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