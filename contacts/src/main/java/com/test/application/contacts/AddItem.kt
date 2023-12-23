package com.test.application.contacts

import com.gb_materialdesign.model.contacts.User

fun interface AddItem {
    fun add(user: User, position: Int)
}