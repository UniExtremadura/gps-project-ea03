package com.example.familycoin.home

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
import com.example.familycoin.gridView.ShopAdapter
import com.example.familycoin.gridView.ShopItem

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
    private lateinit var adapter:ShopAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        shopList = setDataList()
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
        val view = inflater.inflate(R.layout.fragment_shop, container, false)
        gridView = view.findViewById(R.id.gridView)
        adapter = ShopAdapter(requireContext(), shopList)
        gridView.adapter = adapter

        val btnNewReward = view.findViewById<View>(R.id.btnAddReward)

        btnNewReward.setOnClickListener {
            findNavController().navigate(R.id.newRewardFragment)
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