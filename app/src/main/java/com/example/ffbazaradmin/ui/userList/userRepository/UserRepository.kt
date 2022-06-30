package com.example.ffbazaradmin.ui.userList.userRepository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.ffbazaradmin.ui.paymentRequests.model.PaymentRequestModel
import com.example.ffbazaradmin.ui.userList.model.UserModel
import com.google.firebase.database.*

class UserRepository {

    var userReference: DatabaseReference =
        FirebaseDatabase.getInstance().reference.child("Users")
    var userList: MutableList<UserModel> = ArrayList()

    fun getUserList(_userList: MutableLiveData<List<UserModel>>, message: MutableLiveData<String>) {
        userReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (snap in snapshot.children){
                        var model = snap.getValue(UserModel::class.java)
                        if (model != null) {
                            userList.add(model)
                        }
                    }
                    Log.d("TAG", "onDataChange: $snapshot.")
                    _userList.postValue(userList)
                } else {
                    message.postValue("No user Found")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                message.postValue(error.message)
            }
        })

    }


}