package com.gb_materialdesign.ui.main.contacts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gb_materialdesign.model.contacts.User
import com.gb_materialdesign.model.contacts.UserService
import com.gb_materialdesign.repository.contacts.ContactsRepository
import com.gb_materialdesign.repository.contacts.ContactsRepositoryImpl

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
}