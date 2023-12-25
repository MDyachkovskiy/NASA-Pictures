package com.test.application.remote_data.api

import com.github.javafaker.Faker
import com.test.application.core.domain.contacts.User
import java.util.*
import kotlin.random.Random

class UserService {

    private var users = mutableListOf<User>()

    init {
        val faker = Faker.instance()
        IMAGES.shuffle()
        users = (1..100).map { User(
            id = it,
            name = faker.name().name(),
            company = faker.company().name(),
            photo = IMAGES[it % IMAGES.size],
            type = if(Random.nextBoolean()) 1 else 2
        ) } as MutableList<User>
    }

    fun getUsers(): List<User> {
        return users
    }

    fun deleteUser(indexToDelete: Int) {
        if(indexToDelete!= -1) {
            users.removeAt(indexToDelete)
        }
    }

    fun addUser(user: User, position: Int) {
            users.add(position, user)
    }

    fun moveUser(user: User, moveBy: Int) {
        val oldIndex = users.indexOfFirst{it.id == user.id}
        if(oldIndex == -1) return
        val newIndex = (oldIndex + moveBy)
        if(newIndex < 0 || newIndex >= users.size) return
        Collections.swap(users, oldIndex, newIndex)
    }

    companion object {
        private val IMAGES = mutableListOf(
            "https://i.pravatar.cc/150?img=54",
            "https://i.pravatar.cc/150?img=69",
            "https://i.pravatar.cc/150?img=68",
            "https://i.pravatar.cc/150?img=60",
            "https://i.pravatar.cc/150?img=61",
            "https://i.pravatar.cc/150?img=56",
            "https://i.pravatar.cc/150?img=49",
            "https://i.pravatar.cc/150?img=47",
            "https://i.pravatar.cc/150?img=45",
            "https://i.pravatar.cc/150?img=36",
            "https://i.pravatar.cc/150?img=35",
            "https://i.pravatar.cc/150?img=32",
            "https://i.pravatar.cc/150?img=26",
            "https://i.pravatar.cc/150?img=18",
            "https://i.pravatar.cc/150?img=14",
            "https://i.pravatar.cc/150?img=12",
            "https://i.pravatar.cc/150?img=3"
        )
    }
}