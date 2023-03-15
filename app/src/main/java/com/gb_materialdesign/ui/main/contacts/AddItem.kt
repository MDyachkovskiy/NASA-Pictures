package com.gb_materialdesign.ui.main.contacts

import com.gb_materialdesign.model.contacts.User

fun interface AddItem {
    fun add(user: User, position: Int)
}