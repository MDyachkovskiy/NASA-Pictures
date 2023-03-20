package com.gb_materialdesign.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gb_materialdesign.R
import com.gb_materialdesign.adapters.difutils.Change
import com.gb_materialdesign.adapters.difutils.DiffUtilCallback
import com.gb_materialdesign.adapters.difutils.createCombinedPayload
import com.gb_materialdesign.databinding.ItemAlternativeContactsListBinding
import com.gb_materialdesign.databinding.ItemContactsListBinding
import com.gb_materialdesign.model.contacts.User
import com.gb_materialdesign.ui.main.contacts.AddItem
import com.gb_materialdesign.ui.main.contacts.DragAndMoveItem
import com.gb_materialdesign.ui.main.contacts.MoveItem
import com.gb_materialdesign.ui.main.contacts.RemoveItem

class ContactsListAdapter(
    private var contacts: List<User>,
    private val callbackAdd: AddItem,
    private val callbackRemove: RemoveItem,
    private val callbackMove: MoveItem,
    private val callbackDragAndMove: DragAndMoveItem
) : RecyclerView.Adapter<ContactsListAdapter.BaseViewHolder>(), ItemTouchHelperAdapter {

    abstract class BaseViewHolder(view: View): RecyclerView.ViewHolder(view){
        abstract fun bind(user: User)
    }

    inner class ContactsViewHolder(
        val binding: ItemContactsListBinding
    ) : BaseViewHolder(binding.root), ItemTouchHelperViewHolder {

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

                binding.addButton.setOnClickListener {
                    callbackAdd.add(contacts[layoutPosition], layoutPosition)
                }

                binding.removeButton.setOnClickListener {
                    callbackRemove.remove(layoutPosition)
                }
            }
        }

        override fun onItemSelect() {
            binding.root.setBackgroundColor(ContextCompat.getColor(itemView.context,
                R.color.grey_style_3))
        }

        override fun onItemClear() {
            binding.root.setBackgroundColor(0)
        }
    }

    inner class AlternativeContactsViewHolder(
        val binding: ItemAlternativeContactsListBinding
    ) : BaseViewHolder(binding.root), ItemTouchHelperViewHolder {

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

                moveUpButton.setOnClickListener{
                    callbackMove.move(contacts[layoutPosition],-1, layoutPosition)
                }
                moveDownButton.setOnClickListener{
                    callbackMove.move(contacts[layoutPosition],1, layoutPosition)
                }
            }
        }

        override fun onItemSelect() {
            binding.root.setBackgroundColor(ContextCompat.getColor(itemView.context,
            R.color.grey_style_3))
        }

        override fun onItemClear() {
            binding.root.setBackgroundColor(0)
        }
    }

    fun setNewContactsListAfterAdd(newContacts: List<User>, position: Int) {
        contacts = newContacts
        notifyItemInserted(position)
    }

    fun setNewContactsListAfterRemove(newContacts: List<User>, position: Int) {
        contacts = newContacts
        notifyItemRemoved(position)
    }

    fun setNewContactsAfterMoveUp(newContacts: List<User>, position: Int) {
        contacts = newContacts
        notifyItemMoved(position, position+1)
    }

    fun setNewContactsAfterMoveDown(newContacts: List<User>, position: Int) {
        contacts = newContacts
        notifyItemMoved(position, position-1)
    }

    fun setNewContactsAfterMove(newContacts: List<User>, fromPosition: Int, toPosition: Int) {
        contacts = newContacts
        notifyItemMoved(fromPosition, toPosition)
    }

    fun setNewContactsForDiffUtils(newContacts: List<User>) {
        val diff = DiffUtil.calculateDiff(DiffUtilCallback(contacts, newContacts))
        diff.dispatchUpdatesTo(this)
        contacts = newContacts
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

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        callbackDragAndMove.dragAndMove(contacts[fromPosition], fromPosition,toPosition)
    }

    override fun onItemDismiss(position: Int) {
        callbackRemove.remove(position)
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {

        if(payloads.isEmpty()){
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val createCombinedPayload = createCombinedPayload(payloads as List<Change<User>>)
            if(createCombinedPayload.newList.name != createCombinedPayload.oldList.name){
                holder.itemView.findViewById<TextView>(R.id.user_name_text_view).text =
                    createCombinedPayload.newList.name
            }
            if(createCombinedPayload.newList.company != createCombinedPayload.oldList.company) {
                holder.itemView.findViewById<TextView>(R.id.user_name_text_view).text =
                    createCombinedPayload.newList.company
            }
        }
    }

}