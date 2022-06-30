package com.example.ffbazaradmin.ui.userList.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ffbazaradmin.ui.userList.model.UserModel
import com.example.ffbazaradmin.ui.userList.userRepository.UserRepository

class UserListViewModel : ViewModel() {
    private var userRapo = UserRepository()
    private var _userList = MutableLiveData<List<UserModel>>()
    var userList: LiveData<List<UserModel>> = _userList

    private var _message = MutableLiveData<String>()
    var message: LiveData<String> = _message

    fun getUser() {
        userRapo.getUserList(_userList, _message)
    }
}