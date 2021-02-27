package com.blandinf.androidtesttp.adapters

import com.blandinf.androidtesttp.models.User

interface ListUsersHandler {
    fun onClickDelete(user: User)
}