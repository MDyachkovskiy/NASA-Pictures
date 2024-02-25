package com.test.application.contacts.adapter

import androidx.recyclerview.widget.DiffUtil
import com.test.application.core.domain.contacts.User

class DiffUtilCallback(
    private val oldList: List<User>,
    private val newList: List<User>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].photo == newList[newItemPosition].photo&&
                oldList[oldItemPosition].name == newList[newItemPosition].name&&
                oldList[oldItemPosition].company == newList[newItemPosition].company
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]

        return Change(old, new)
    }
}

