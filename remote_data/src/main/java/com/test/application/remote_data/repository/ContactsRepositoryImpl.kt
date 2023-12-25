package com.test.application.remote_data.repository

import com.test.application.core.domain.contacts.User
import com.test.application.core.repository.ContactsRepository
import com.test.application.core.utils.AppState
import com.test.application.remote_data.api.UserService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ContactsRepositoryImpl (
    private val userSource: UserService
) : ContactsRepository {

    override suspend fun getContactsList(): Flow<AppState> = flow {
        try{
            emit(AppState.Loading)
            val users = userSource.getUsers()
            emit(AppState.Success(users))
        } catch (e: Exception) {
            emit(AppState.Error(e))
        }
    }

    override fun getCurrentContacts(): List<User> {
        return userSource.getUsers()
    }

    override suspend fun deleteUser(position: Int) {
        userSource.deleteUser(position)
    }

    override suspend fun addUser(user: User, position: Int) {
        userSource.addUser(user, position)
    }

    override suspend fun moveContact(user: User, moveBy: Int) {
        userSource.moveUser(user, moveBy)
    }
}