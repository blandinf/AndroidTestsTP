package com.blandinf.androidtesttp

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.blandinf.androidtesttp.injections.Injection
import com.blandinf.androidtesttp.repositories.UserRepository
import com.blandinf.androidtesttp.utils.RecyclerViewUtils
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 * Testing MainActivity screen.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class UserListInstrumentedTest {
    private var userRepository: UserRepository? = null
    private var currentUsersSize = -1

    @Rule @JvmField
    val mActivityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setup() {
        mActivityRule.activity
        userRepository = Injection.createUserRepository()
        currentUsersSize = userRepository!!.getUsers().size
    }

    @Test
    fun checkIfRecyclerViewIsNotEmpty() {
        Espresso.onView(withId(R.id.users_list)).check(RecyclerViewUtils.ItemCount(currentUsersSize))
    }

    @Test
    fun checkIfAddingRandomUserIsWorking() {
        Espresso.onView(withId(R.id.users_list_fab)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.users_list)).check(RecyclerViewUtils.ItemCount(currentUsersSize + 1))
    }

    @Test
    fun checkIfRemovingUserIsWorking() {
        Espresso.onView(withId(R.id.users_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, RecyclerViewUtils.clickChildView(R.id.item_list_user_delete_button)))
        Espresso.onView(withId(R.id.users_list)).check(RecyclerViewUtils.ItemCount(currentUsersSize - 1))
    }
}