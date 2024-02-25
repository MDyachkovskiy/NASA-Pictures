package com.test.application.contacts.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.application.core.domain.contacts.User
import com.test.application.core.repository.ContactsRepository
import com.test.application.core.utils.AppState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ContactsViewModel (
    private val contactsRepository: ContactsRepository
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<AppState>(AppState.Loading)
    val stateFlow: StateFlow<AppState> = _stateFlow.asStateFlow()


    fun getContacts() {
        viewModelScope.launch {
            contactsRepository.getContactsList().collect {
                _stateFlow.value = it
            }
        }
    }

    fun getUpdatedContacts(): List<User> {
        return contactsRepository.getCurrentContacts()
    }

    fun deleteContact(position: Int) {
        viewModelScope.launch {
            contactsRepository.deleteUser(position)

        }
    }

    fun addContact(user: User, position: Int) {
        viewModelScope.launch {
            contactsRepository.addUser(user, position)
        }
    }

    fun moveContact(user: User, moveBy: Int) {
        viewModelScope.launch {
            contactsRepository.moveContact(user, moveBy)
        }
    }
}