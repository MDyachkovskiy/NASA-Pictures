package com.gb_materialdesign.model.contacts

data class User(
    val id: Int,
    val photo: String,
    val name: String,
    val company: String,
)

data class UserDetails (
    val user: User,
    val details: String
)