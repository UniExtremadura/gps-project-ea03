import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.familycoin.R
import com.example.familycoin.recyclerView.FamilyItem

class FamilyAdapter(private val familyList: List<FamilyItem>) : RecyclerView.Adapter<FamilyAdapter.FamilyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FamilyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_list, parent, false)
        return FamilyViewHolder(view)
    }

    override fun onBindViewHolder(holder: FamilyViewHolder, position: Int) {
        val familyItem = familyList[position]

        holder.imageView.setImageResource(familyItem.imageRes!!)
        holder.textView.text = familyItem.name
    }

    override fun getItemCount(): Int {
        return familyList.size
    }

    class FamilyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.recyclerItemImage)
        val textView: TextView = itemView.findViewById(R.id.recyclerItemName)
    }
}