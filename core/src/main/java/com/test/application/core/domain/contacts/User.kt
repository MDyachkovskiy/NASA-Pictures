package com.test.application.core.domain.contacts

data class User(
    val id: Int,
    val photo: String,
    val name: String,
    val company: String,
    val type: Int
)