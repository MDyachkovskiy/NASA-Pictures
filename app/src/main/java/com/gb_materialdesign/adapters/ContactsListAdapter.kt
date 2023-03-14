package com.gb_materialdesign.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gb_materialdesign.R
import com.gb_materialdesign.databinding.ItemContactsListBinding
import com.gb_materialdesign.model.contacts.User


class ContactsListAdapter(
    private val contacts: List<User>
) : RecyclerView.Adapter<ContactsListAdapter.ContactsViewHolder>() {

    class ContactsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(user: User) {
            val binding = ItemContactsListBinding.bind(itemView)
            with(binding) {

                userNameTextView.text = user.name
                userCompanyTextView.text = user.company

                if (user.photo.isNotBlank()) {
                    Glide.with(photoImageView.context)
                        .load(user.photo)
                        .circleCrop()
                        .placeholder(R.drawable.ic_user_avatar)
                        .error(R.drawable.ic_user_avatar)
                        .into(photoImageView)
                }else{
                    photoImageView.setImageResource(R.drawable.ic_user_avatar)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val binding = ItemContactsListBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return ContactsViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val user = contacts[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return contacts.size
    }
}