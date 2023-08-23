package com.example.sqllite.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sqllite.databinding.ItemRvBinding
import com.example.sqllite.models.MyContact

class MyContactAdapter(var list: ArrayList<MyContact>,val rvAction: RvAction) :
    RecyclerView.Adapter<MyContactAdapter.Vh>() {

    inner class Vh(val itemRvBinding: ItemRvBinding) : RecyclerView.ViewHolder(itemRvBinding.root) {
        fun onBind(myContact: MyContact,position: Int) {
            itemRvBinding.tvName.text = myContact.name
            itemRvBinding.tvNumber.text = myContact.number
            itemRvBinding.root.setOnLongClickListener {
                rvAction.deleteContact(myContact,position)
                true
            }
            itemRvBinding.root.setOnClickListener {
                rvAction.updateContact(myContact,position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position],position)
    }

    override fun getItemCount(): Int = list.size

}
interface RvAction{
    fun deleteContact(myContact: MyContact,position: Int)
    fun updateContact(myContact: MyContact,position: Int)
}