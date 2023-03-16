package com.gb_materialdesign.ui.main.contacts

import com.gb_materialdesign.model.contacts.User

fun interface DragAndMoveItem {
    fun dragAndMove(user: User, fromPosition: Int, toPosition: Int)
}