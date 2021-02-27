package com.blandinf.androidtesttp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.blandinf.androidtesttp.R
import com.blandinf.androidtesttp.databinding.ItemListUsersBinding
import com.blandinf.androidtesttp.models.User
import com.blandinf.androidtesttp.utils.UserDiffCallback
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ListUsersAdapter(items: List<User>, private val callback: ListUsersHandler) : RecyclerView.Adapter<ListUsersAdapter.ViewHolder>() {

    private var mUsers: List<User> = items
    private lateinit var binding: ItemListUsersBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemListUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user: User = mUsers[position]

        holder.binding.itemListUserUsername.text = user.login

        val context = binding.root.context
        Glide.with(context)
            .load(user.avatarUrl)
            .apply(RequestOptions.circleCropTransform())
            .placeholder(R.drawable.ic_person)
            .error(R.drawable.ic_person)
            .skipMemoryCache(false)
            .into(holder.binding.itemListUserAvatar)

        holder.binding.itemListUserDeleteButton.setOnClickListener {
            callback.onClickDelete(user)
        }
    }

    override fun getItemCount(): Int {
        return mUsers.size
    }

    // PUBLIC API ---
    fun updateList(newList: List<User>) {
        val diffResult = DiffUtil.calculateDiff(UserDiffCallback(newList, mUsers))
        mUsers = ArrayList(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(var binding: ItemListUsersBinding) : RecyclerView.ViewHolder(binding.root)

}