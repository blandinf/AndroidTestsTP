package com.blandinf.androidtesttp.api

import com.blandinf.androidtesttp.models.User

interface ApiService {
    fun getUsers(): List<User>
    fun generateRandomUser()
    fun deleteUser(user: User)
    fun clear()
}