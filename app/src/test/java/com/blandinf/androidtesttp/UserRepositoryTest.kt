package com.blandinf.androidtesttp

import com.blandinf.androidtesttp.api.FakeApiServiceGenerator
import com.blandinf.androidtesttp.injections.Injection.createUserRepository
import com.blandinf.androidtesttp.models.User
import com.blandinf.androidtesttp.repositories.UserRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.stream.Collectors

@RunWith(JUnit4::class)
class UserRepositoryTest {
    private var userRepository: UserRepository? = null
    private val FAKE_USERS: List<User> = FakeApiServiceGenerator.FAKE_USERS
    private val FAKE_USERS_RANDOM = FakeApiServiceGenerator.FAKE_USERS_RANDOM

    @Before
    fun setup() {
        userRepository = createUserRepository()
    }

    @Test
    fun getUsersWithSuccess() {
        val usersActual = userRepository!!.getUsers()
        val usersExpected = FAKE_USERS
        Assert.assertEquals(usersActual, usersExpected)
    }

    @Test
    fun generateRandomUserWithSuccess() {
        userRepository!!.clear()
        userRepository!!.generateRandomUser()
        val (id, login, avatarUrl) = userRepository!!.getUsers()[0]
        Assert.assertEquals(1, userRepository!!.getUsers().size.toLong())
        Assert.assertTrue(FAKE_USERS_RANDOM.stream().map(User::avatarUrl).collect(Collectors.toList()).contains(avatarUrl))
        Assert.assertTrue(FAKE_USERS_RANDOM.stream().map(User::id).collect(Collectors.toList()).contains(id))
        Assert.assertTrue(FAKE_USERS_RANDOM.stream().map(User::login).collect(Collectors.toList()).contains(login))
    }

    @Test
    fun deleteUserWithSuccess() {
        val userToDelete = userRepository!!.getUsers()[0]
        userRepository!!.deleteUser(userToDelete)
        Assert.assertFalse(userRepository!!.getUsers().contains(userToDelete))
    }
}