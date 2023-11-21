package com.example.familycoin.home
import FamilyAdapter
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
    private lateinit var familyNameTextView: TextView


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
        familyNameTextView = view.findViewById(R.id.textView)

        lifecycleScope.launch {
            setDataList()
        }

        return view
    }

    private suspend fun setDataList() {
        val listFamilyItem: ArrayList<FamilyItem> = ArrayList()
        val listUser: ArrayList<User> = ArrayList()

        val sharedPref = context?.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE)
        val valorString = sharedPref?.getString("username", "default")
        val userUpdate = db.userDao().findByName(valorString!!)

        val familyName = db.familyDao().findById(userUpdate.familyCoinId!!).familyName
        familyNameTextView.text = familyName

        val userList = db.userDao().findByFamilyCoinId(userUpdate.familyCoinId!!) as ArrayList<User>

        for (user in userList) {
            listFamilyItem.add(FamilyItem(user.name, R.drawable.baseline_person_outline_24))
        }

        adapter = FamilyAdapter(listFamilyItem)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }
}