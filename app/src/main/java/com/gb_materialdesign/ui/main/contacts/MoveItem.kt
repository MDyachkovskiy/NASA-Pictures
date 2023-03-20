package com.gb_materialdesign.ui.main.contacts

import com.gb_materialdesign.model.contacts.User

fun interface MoveItem {
    fun move(user: User, moveBy: Int, position: Int)
}