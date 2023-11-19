package com.example.familycoin.home
import FamilyAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.familycoin.R
import com.example.familycoin.recyclerView.FamilyItem

class FamilyFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FamilyAdapter
    private lateinit var familyList: List<FamilyItem>

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
        val list = mutableListOf<FamilyItem>()

        list.add(FamilyItem("Miembro 1", R.drawable.baseline_person_outline_24))
        list.add(FamilyItem("Miembro 2", R.drawable.baseline_person_outline_24))
        list.add(FamilyItem("Miembro 3", R.drawable.baseline_person_outline_24))
        // Añade más elementos según sea necesario

        return list
    }
}