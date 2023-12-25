package com.test.application.contacts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.test.application.contacts.R
import com.test.application.contacts.databinding.ItemAlternativeContactsListBinding
import com.test.application.contacts.databinding.ItemContactsListBinding
import com.test.application.core.domain.contacts.User

class ContactsListAdapter : RecyclerView.Adapter<ContactsListAdapter.BaseViewHolder>() {

    var contacts = mutableListOf<User>()

    var onItemAdd: ((User, Int) -> Unit)? = null
    var onItemRemove: ((Int) -> Unit)? = null
    var onItemMoved: ((User, Int, Int) -> Unit)? = null
    var onItemDragAndMove: ((User, Int, Int) -> Unit)? = null

    fun updateContacts(newContacts: List<User>) {
        val diffCallback = DiffUtilCallback(this.contacts, newContacts)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.contacts.clear()
        this.contacts.addAll(newContacts)
        diffResult.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

        return when (viewType) {
            1 -> {
                val binding = ItemContactsListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                ContactsViewHolder(binding)
            }

            else -> {
                val binding = ItemAlternativeContactsListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                AlternativeContactsViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {

        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val safePayloads = payloads as? List<Change<User>>

            if(safePayloads != null) {
                val combinedPayload = createCombinedPayload(safePayloads)
                if (combinedPayload.newList.name != combinedPayload.oldList.name) {
                    holder.itemView.findViewById<TextView>(R.id.user_name_text_view).text =
                        combinedPayload.newList.name
                }
                if (combinedPayload.newList.company != combinedPayload.oldList.company) {
                    holder.itemView.findViewById<TextView>(R.id.user_name_text_view).text =
                        combinedPayload.newList.company
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun getItemViewType(position: Int): Int {
        return contacts[position].type
    }

    private fun <T> createCombinedPayload(payload: List<Change<T>>): Change<T> {
        assert(payload.isNotEmpty())
        val firstChange = payload.first()
        val lastChange = payload.last()

        return Change(firstChange.oldList, lastChange.newList)
    }


    abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(user: User)
        fun onItemSelect() {
            itemView.setBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.selected_item_color
                )
            )
        }

        fun onItemClear() {
            itemView.setBackgroundColor(0)
        }
    }

    inner class ContactsViewHolder(
        private val binding: ItemContactsListBinding
    ) : BaseViewHolder(binding.root) {

        override fun bind(user: User) {
            displayUserData(user)
            setupButtonListeners(user)
        }

        private fun displayUserData(user: User) {
            with(binding) {
                userNameTextView.text = user.name
                userCompanyTextView.text = user.company
                displayImageData(binding.photoImageView, user.photo)
            }
        }

        private fun setupButtonListeners(user: User) {
            binding.removeButton.setOnClickListener {
                onItemRemove?.invoke(layoutPosition)
            }
            binding.addButton.setOnClickListener {
                onItemAdd?.invoke(user, layoutPosition)
            }
        }
    }


    inner class AlternativeContactsViewHolder(
        private val binding: ItemAlternativeContactsListBinding
    ) : BaseViewHolder(binding.root) {

        override fun bind(user: User) {
            displayUserData(user)
            setupButtonListeners(user)
        }

        private fun displayUserData(user: User) {
            binding.userNameTextView.text = user.name
            binding.userCompanyTextView.text = user.company
            displayImageData(binding.photoImageView, user.photo)
        }

        private fun setupButtonListeners(user: User) {
            binding.moveDownButton.setOnClickListener {
                onItemMoved?.invoke(user, 1, layoutPosition)
            }
            binding.moveUpButton.setOnClickListener {
                onItemMoved?.invoke(user, -1, layoutPosition)
            }
        }
    }

    private fun displayImageData(imageView: ImageView, imageUrl: String) {
        if (imageUrl.isNotBlank()) {
            imageView.load(imageUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_user_avatar)
                error(R.drawable.ic_user_avatar)
            }
        } else {
            imageView.setImageResource(R.drawable.ic_user_avatar)
        }
    }
}