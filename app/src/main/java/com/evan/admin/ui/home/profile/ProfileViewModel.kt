package com.evan.admin.ui.home.profile

import android.util.Log
import androidx.lifecycle.ViewModel;
import com.evan.admin.data.db.entities.User
import com.evan.admin.data.repositories.UserRepository
import com.evan.admin.util.Coroutines
import com.evan.admin.util.lazyDeferred


class ProfileViewModel(
    private val  repository: UserRepository
) : ViewModel() {

    fun user() = repository.getUser()
   // fun userList() = repository.getUserList()
    val quotes by lazyDeferred {
        repository.getUserList()
    }

    fun saveUser(user: User){
        Coroutines.main {
            Log.e("sdfds","Sds"+user.created_at)
            repository.saveUser(user)
        }

    }


}
