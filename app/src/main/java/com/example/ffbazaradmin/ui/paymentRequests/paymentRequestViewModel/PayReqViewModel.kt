package com.example.ffbazaradmin.ui.paymentRequests.paymentRequestViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ffbazaradmin.ui.paymentRequests.model.PaymentRequestModel
import com.example.ffbazaradmin.ui.paymentRequests.paymentRequestRepo.PayReqRepo

class PayReqViewModel : ViewModel() {
    private val repo = PayReqRepo()

    private val _paymentRequestList = MutableLiveData<List<PaymentRequestModel>>()
    val paymentRequestListLiveData: LiveData<List<PaymentRequestModel>> = _paymentRequestList

    private val _approve_message = MutableLiveData<String>()
    private val _reject_message = MutableLiveData<String>()
    val approve_message: LiveData<String> = _approve_message
    val reject_message: LiveData<String> = _reject_message

    fun fatchPaymentRequestList() {
        repo.getPaymentRequestReference(_paymentRequestList)
    }


    fun setApproved(edtUid: String, userId: String, key: String) {
        repo.setApproved(edtUid, userId, key,_approve_message)
    }

    fun setRejected(userId: String, key: String) {
        Log.d("TAG", "PayReqViewModel: ")
        repo.setRejected(userId, key,_reject_message)
    }
}