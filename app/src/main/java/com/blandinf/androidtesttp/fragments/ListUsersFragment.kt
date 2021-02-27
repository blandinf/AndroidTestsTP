package com.blandinf.androidtesttp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.blandinf.androidtesttp.adapters.ListUsersAdapter
import com.blandinf.androidtesttp.adapters.ListUsersHandler
import com.blandinf.androidtesttp.api.FakeApiService
import com.blandinf.androidtesttp.databinding.ListUsersFragmentBinding
import com.blandinf.androidtesttp.injections.Injection
import com.blandinf.androidtesttp.models.User
import com.blandinf.androidtesttp.repositories.UserRepository

class ListUsersFragment: Fragment(), ListUsersHandler {

    @VisibleForTesting
    lateinit var repository: UserRepository
    private lateinit var binding: ListUsersFragmentBinding
    private lateinit var users: List<User>
    private lateinit var adapter: ListUsersAdapter
    private lateinit var apiService: FakeApiService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ListUsersFragmentBinding.inflate(inflater, container, false)
        binding.usersList.layoutManager = LinearLayoutManager(context)
        binding.usersList.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apiService = FakeApiService()
        users = UserRepository.getInstance(apiService).getUsers()
        adapter = ListUsersAdapter(users, this)
        binding.usersList.adapter = adapter
        configureFab()
        repository = Injection.createUserRepository()
    }


    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun configureFab() {
        binding.usersListFab.setOnClickListener { view: View? ->
            repository.generateRandomUser()
            loadData()
        }
    }

    private fun loadData() {
        adapter.updateList(Injection.createUserRepository().getUsers())
    }

    override fun onClickDelete(user: User) {
        Log.d(ListUsersFragment::class.java.name, "User tries to delete a item.")
        repository.deleteUser(user)
        loadData()
    }
}