package com.test.application.core.repository

import com.test.application.core.domain.contacts.User
import com.test.application.core.utils.AppState
import kotlinx.coroutines.flow.Flow

interface ContactsRepository {

    suspend fun getContactsList(): Flow<AppState>

    fun getCurrentContacts(): List<User>

    suspend fun deleteUser(position: Int)

    suspend fun addUser(user: User, position: Int)

    suspend fun moveContact(user: User, moveBy: Int)

}