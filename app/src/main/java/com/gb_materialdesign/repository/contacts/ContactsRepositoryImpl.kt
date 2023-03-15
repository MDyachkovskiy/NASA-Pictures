package com.gb_materialdesign.repository.contacts

import com.gb_materialdesign.model.contacts.User
import com.gb_materialdesign.model.contacts.UserService

class ContactsRepositoryImpl (
    private val userSource: UserService
        ) : ContactsRepository {

    override fun getContactsList(): List<User> {
        return userSource.getUsers()
    }

    override fun deleteUser(position: Int) {
        userSource.deleteUser(position)
    }

    override fun addUser(user: User, position: Int) {
        userSource.addUser(user, position)
    }

    override fun moveContact(user: User, moveBy: Int) {
        userSource.moveUser(user, moveBy)
    }
}