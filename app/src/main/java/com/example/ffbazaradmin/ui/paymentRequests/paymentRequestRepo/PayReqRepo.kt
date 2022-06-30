package com.example.ffbazaradmin.ui.paymentRequests.paymentRequestRepo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.ffbazaradmin.ui.paymentRequests.model.PaymentRequestModel
import com.google.firebase.database.*
import kotlin.math.log

class PayReqRepo {
    private val TAG = "PayReqRepo"
    var paymentRequestReference: DatabaseReference =
        FirebaseDatabase.getInstance().reference.child("PaymentRequest")
    var paymentRequestList: MutableList<PaymentRequestModel> = ArrayList()

    //  var _message = MutableLiveData<String>()

    fun getPaymentRequestReference(liveData: MutableLiveData<List<PaymentRequestModel>>) {
        //    isProgressing = true
        paymentRequestReference.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (snap in snapshot.children) {
                            getOnlyRequestedPayment(snap.key.toString(), liveData)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    //  progressbarObservable?.postValue(true)
                }
            })
    }

    private fun getOnlyRequestedPayment(
        userId: String,
        liveData: MutableLiveData<List<PaymentRequestModel>>
    ) {
        paymentRequestList.clear()
        val query: Query =
            paymentRequestReference.child(userId).orderByChild("approvedOrNot").equalTo("Requested")
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snap in snapshot.children) {

                    var model = snap.getValue(PaymentRequestModel::class.java)
                    if (model != null) {
                        paymentRequestList.add(model)
                        //  isProgressing = false
                    }
                }
                liveData.postValue(paymentRequestList)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun setApproved(
        edtUid: String,
        userId: String,
        key: String,
        _message: MutableLiveData<String>
    ) {
        val map: MutableMap<String, Any> = HashMap()
        map["uid"] = edtUid
        map["approvedOrNot"] = "Approved"
        paymentRequestReference.child(userId).child(key).updateChildren(map).addOnCompleteListener {
            if (it.isSuccessful) {
                _message.postValue("Approved Successful")
            }
        }.addOnFailureListener {
            _message.postValue(it.message.toString())

        }
    }

    fun setRejected(userId: String, key: String, _message: MutableLiveData<String>) {
        paymentRequestReference.child(userId).child(key).child("approvedOrNot").setValue("Rejected")
            .addOnCompleteListener {
                if (it.isSuccessful) {
                      _message.postValue( "Rejected")
                      Log.d(TAG, "setRejected: Rejected")
                }
            }.addOnFailureListener {
                 _message.postValue(it.message.toString())

            }
    }

}


