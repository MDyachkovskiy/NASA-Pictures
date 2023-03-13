package com.gb_materialdesign.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gb_materialdesign.databinding.ItemContactsListBinding
import com.gb_materialdesign.model.contacts.User

class ContactsListAdapter(
    private val contacts: List<User>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemContactsListBinding.inflate(LayoutInflater.from(parent.context))
        return ContactsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = contacts.size

    class ContactsViewHolder(
        val binding: ItemContactsListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

    }

}