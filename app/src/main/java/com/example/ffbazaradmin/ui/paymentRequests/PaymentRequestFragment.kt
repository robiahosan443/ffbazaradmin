package com.example.ffbazaradmin.ui.paymentRequests

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ffbazaradmin.R
import com.example.ffbazaradmin.databinding.FragmentPaymentRequestBinding
import com.example.ffbazaradmin.ui.paymentRequests.adapter.PaymentRequestAdapter
import com.example.ffbazaradmin.ui.paymentRequests.model.PaymentRequestModel
import com.example.ffbazaradmin.ui.paymentRequests.paymentRequestViewModel.PayReqViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class PaymentRequestFragment : Fragment() {

    private var _binding: FragmentPaymentRequestBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var progressDialog: ProgressDialog? = null
    var adapterPaymentRequest: PaymentRequestAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPaymentRequestBinding.inflate(inflater, container, false)
        progressDialog = ProgressDialog(activity)
        progressDialog!!.setTitle("Warning...")
        progressDialog!!.setMessage("Application is loading, please wait")
        progressDialog!!.show()

        val viewModel: PayReqViewModel = ViewModelProvider(this)[PayReqViewModel::class.java]

        binding.lifecycleOwner = this


        viewModel.fatchPaymentRequestList()
        viewModel.paymentRequestListLiveData.observe(viewLifecycleOwner) { paymentRequestList ->
            binding.rvPaymentRequests.layoutManager = LinearLayoutManager(requireContext())
            adapterPaymentRequest = PaymentRequestAdapter(paymentRequestList)
            binding.rvPaymentRequests.adapter = adapterPaymentRequest
            adapterPaymentRequest!!.notifyDataSetChanged()
            progressDialog!!.dismiss()
            adapterPaymentRequest!!.onItemClick =
                { paymentList: List<PaymentRequestModel>, pos: Int ->
                    //  viewModel.setApproved(edtUid, paymentList[pos].userId, paymentList[pos].key)
                    findNavController().navigate(
                        R.id.action_PaymentRequestFragment_to_paymentDetailsFragment,
                        bundleOf(

                            "price" to paymentList[pos].price,
                            "diamondnum" to paymentList[pos].diamondnum,
                            "userId" to paymentList[pos].userId,
                            "subCategory" to paymentList[pos].subCategory,
                            "uid" to paymentList[pos].uid,
                            "approvedOrNot" to paymentList[pos].approvedOrNot,
                            "key" to paymentList[pos].key,
                            )
                    )
                }
        }
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}