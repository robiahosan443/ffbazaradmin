package com.example.ffbazaradmin.ui.paymentRequests.paymentDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.ffbazaradmin.databinding.FragmentPaymentDetailsBinding
import com.example.ffbazaradmin.ui.paymentRequests.paymentRequestViewModel.PayReqViewModel


class PaymentDetailsFragment : Fragment() {

    private var _binding: FragmentPaymentDetailsBinding? = null
    private val binding get() = _binding!!
    private var TAG = "PaymentDetailsFragment"

    private var price: String? = null
    private var diamondnum: String? = null
    private var userId: String? = null
    private var subCategory: String? = null
    private var uid: String? = null
    private var approvedOrNot: String? = null
    private var key: String? = null
    private var edtUid: String? = null
    private var spApprovedOrNot: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            price = it.getString("price")
            diamondnum = it.getString("diamondnum")
            userId = it.getString("userId")
            subCategory = it.getString("subCategory")
            uid = it.getString("uid")
            approvedOrNot = it.getString("approvedOrNot")
            key = it.getString("key")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPaymentDetailsBinding.inflate(inflater, container, false)
        val viewModel: PayReqViewModel = ViewModelProvider(this)[PayReqViewModel::class.java]


        binding.txtSubcategory.text = subCategory
        binding.txtUid.text = uid
        binding.txtPrice.text = "৳ $price"
        binding.txtUserId.text = userId

        if (subCategory != "GarenaSheel") {
            binding.edtUid.visibility = View.GONE
        } else {
            binding.edtUid.visibility = View.VISIBLE
            binding.txtUid.visibility = View.GONE
        }
        if (diamondnum!!.isNotEmpty()) {
            binding.txtDiamondNum.text = diamondnum
        } else {
            binding.txtDiamondNum.text = "Membership Request"
        }


        binding.btnProceed.setOnClickListener {
            spApprovedOrNot = binding.spApproveOrNot.selectedItem.toString()
            if (binding.edtUid.visibility == View.VISIBLE) {
                edtUid = binding.edtUid.text.toString()

                if (binding.edtUid.visibility == View.VISIBLE && binding.spApproveOrNot.selectedItemPosition == 1) {
                    if (binding.spApproveOrNot.selectedItemPosition == 1) {
                        viewModel.setRejected(userId!!, key!!)
                        viewModel.reject_message.observe(viewLifecycleOwner) {
                            Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                } else if (edtUid!!.isEmpty()) {
                        Toast.makeText(activity, "Please enter an Uid", Toast.LENGTH_SHORT).show()

                    } else {
                        if (binding.spApproveOrNot.selectedItemPosition == 0) {
                            viewModel.setApproved(edtUid!!, userId!!, key!!)
                            viewModel.approve_message.observe(viewLifecycleOwner) {
                                Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
                            }

                            if (binding.spApproveOrNot.selectedItemPosition == 1) {
                                viewModel.setRejected(userId!!, key!!)
                                viewModel.reject_message.observe(viewLifecycleOwner) {
                                    Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        }
                    }
            } else {
                if (binding.spApproveOrNot.selectedItemPosition == 0) {
                    viewModel.setApproved(uid!!, userId!!, key!!)
                    viewModel.approve_message.observe(viewLifecycleOwner) {
                        Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
                if (binding.spApproveOrNot.selectedItemPosition == 1) {
                    viewModel.setRejected(userId!!, key!!)
                    viewModel.reject_message.observe(viewLifecycleOwner) {
                        Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }

        return binding.root
    }

}