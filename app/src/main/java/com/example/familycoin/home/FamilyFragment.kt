package com.example.familycoin.home
import FamilyAdapter
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.familycoin.R
import com.example.familycoin.database.Database
import com.example.familycoin.databinding.FragmentCreateFamilyBinding
import com.example.familycoin.databinding.FragmentFamilyBinding
import com.example.familycoin.gridView.TaskItem
import com.example.familycoin.model.User
import com.example.familycoin.recyclerView.FamilyItem
import kotlinx.coroutines.launch

class FamilyFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FamilyAdapter
    private lateinit var familyList: List<FamilyItem>
    private lateinit var binding: FragmentFamilyBinding
    private lateinit var db: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = Database.getInstance(requireContext())!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_family, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        familyList = setDataList()

        adapter = FamilyAdapter(familyList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        return view
    }

    private fun setDataList(): List<FamilyItem> {
        var listFamilyItem: ArrayList<FamilyItem> = ArrayList()
        var listUser: ArrayList<User> = ArrayList()

        lifecycleScope.launch {
            val sharedPref = context?.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE)
            val valorString = sharedPref?.getString("username", "default")
            var userUpdate = db.userDao().findByName(valorString!!)
            listUser = db.userDao().findByFamilyCoinId(userUpdate.familyCoinId!!) as ArrayList<User>
        }

        for(user in listUser){
            listFamilyItem.add(FamilyItem(user.name, R.drawable.baseline_person_outline_24))
        }

        return listFamilyItem
    }
}