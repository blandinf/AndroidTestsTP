package com.blandinf.androidtesttp.injections

import com.blandinf.androidtesttp.api.FakeApiService
import com.blandinf.androidtesttp.repositories.UserRepository

object Injection {
    @JvmStatic
    fun createUserRepository(): UserRepository {
        return UserRepository(FakeApiService())
    }
}