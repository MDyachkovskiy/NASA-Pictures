package com.gb_materialdesign.repository.contacts

import com.gb_materialdesign.model.contacts.User
import com.gb_materialdesign.model.contacts.UserService

class ContactsRepositoryImpl (
    private val userSource: UserService
        ) : ContactsRepository {

    override fun getContactsList(): List<User> {
        return userSource.getUsers()
    }
}