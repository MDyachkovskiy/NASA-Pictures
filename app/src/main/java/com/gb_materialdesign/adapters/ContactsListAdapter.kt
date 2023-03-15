package com.gb_materialdesign.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gb_materialdesign.R
import com.gb_materialdesign.databinding.ItemAlternativeContactsListBinding
import com.gb_materialdesign.databinding.ItemContactsListBinding
import com.gb_materialdesign.model.contacts.User


class ContactsListAdapter(
    private val contacts: List<User>
) : RecyclerView.Adapter<ContactsListAdapter.BaseViewHolder>() {

    abstract class BaseViewHolder(view: View): RecyclerView.ViewHolder(view){
        abstract fun bind(user: User)
    }

    class ContactsViewHolder(
        val binding: ItemContactsListBinding
    ) : BaseViewHolder(binding.root) {

        override fun bind(user: User) {
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

    class AlternativeContactsViewHolder(
        val binding: ItemAlternativeContactsListBinding
    ) : BaseViewHolder(binding.root) {

        override fun bind(user: User) {
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

    override fun getItemViewType(position: Int): Int {
        return contacts[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

        return when(viewType){
            1 -> {
                val binding = ItemContactsListBinding.inflate(LayoutInflater.from(parent.context),
                    parent, false)
                ContactsViewHolder(binding)
            }
            else -> {
                val binding = ItemAlternativeContactsListBinding.inflate(LayoutInflater.from(parent.context),
                    parent, false)
                AlternativeContactsViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    override fun getItemCount(): Int {
        return contacts.size
    }
}