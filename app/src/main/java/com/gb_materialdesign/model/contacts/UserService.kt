package com.gb_materialdesign.model.contacts

import com.github.javafaker.Faker

class UserService {

    private var users = mutableListOf<User>()

    init {
        val faker = Faker.instance()
        IMAGES.shuffle()
        val generatedUsers = (1..100).map { User(
            id = it.toInt(),
            name = faker.name().name(),
            company = faker.company().name(),
            photo = IMAGES[it % IMAGES.size]
        ) }
    }

    fun getUsers(): List<User> {
        return users
    }

    fun deleteUser(user: User) {
        val indexToDelete = users.indexOfFirst{it.id == user.id}
        if(indexToDelete!= -1) {
            users.removeAt(indexToDelete)
        }
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