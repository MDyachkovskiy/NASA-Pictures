package com.test.application.contacts

import com.gb_materialdesign.model.contacts.User

fun interface DragAndMoveItem {
    fun dragAndMove(user: User, fromPosition: Int, toPosition: Int)
}