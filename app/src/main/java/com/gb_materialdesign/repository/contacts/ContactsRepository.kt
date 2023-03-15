package com.gb_materialdesign.repository.contacts

import com.gb_materialdesign.model.contacts.User


interface ContactsRepository {

    fun getContactsList(): List<User>

    fun deleteUser(position: Int)

    fun addUser(user: User, position: Int)

}