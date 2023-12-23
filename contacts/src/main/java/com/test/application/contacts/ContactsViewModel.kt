package com.test.application.contacts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gb_materialdesign.model.contacts.User
import com.gb_materialdesign.model.contacts.UserService
import com.test.application.core.repository.ContactsRepository
import com.test.application.remote_data.repository.ContactsRepositoryImpl

class ContactsViewModel (
    private val liveData: MutableLiveData<List<User>> = MutableLiveData(),
    private val contactsRepository: ContactsRepository =
        ContactsRepositoryImpl(UserService())
) : ViewModel() {

    fun getLiveData() : MutableLiveData<List<User>>{
        return liveData
    }

    fun getContacts() {
        val contacts = contactsRepository.getContactsList()
        liveData.postValue(contacts)
    }

    fun getUpdatedContacts(): List<User> {
        return contactsRepository.getContactsList()
    }

    fun deleteContact(position: Int) {
        contactsRepository.deleteUser(position)
    }

    fun addContact(user: User, position: Int) {
        contactsRepository.addUser(user, position)
    }

    fun moveContact(user: User, moveBy: Int) {
        contactsRepository.moveContact(user, moveBy)
    }

}