package com.blandinf.androidtesttp.repositories

import com.blandinf.androidtesttp.api.ApiService
import com.blandinf.androidtesttp.models.User

class UserRepository(private val apiService: ApiService) {

    fun getUsers(): List<User> = apiService.getUsers()

    fun generateRandomUser() = apiService.generateRandomUser()

    fun deleteUser(user: User?) {
        if (user != null) {
            apiService.deleteUser(user)
        }
    }

    fun clear () = apiService.clear()

    companion object {
        private var instance: UserRepository? = null

        // On crée un méthode qui retourne l'instance courante du repository si elle existe ou en crée une nouvelle sinon
        fun getInstance(apiService: ApiService): UserRepository {
            if (instance == null) {
                instance = UserRepository(apiService = apiService)
            }
            return instance!!
        }
    }
}