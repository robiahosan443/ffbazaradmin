package com.example.ffbazaradmin.ui.userList

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ffbazaradmin.R
import com.example.ffbazaradmin.databinding.FragmentHomeBinding
import com.example.ffbazaradmin.databinding.FragmentUserListBinding
import com.example.ffbazaradmin.ui.adsSettings.adsViewModel.AdsViewModel
import com.example.ffbazaradmin.ui.paymentRequests.adapter.PaymentRequestAdapter
import com.example.ffbazaradmin.ui.userList.adapter.AdapterUserList
import com.example.ffbazaradmin.ui.userList.viewModel.UserListViewModel

class UserListFragment : Fragment() {
    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!
    private var adapterUserList: AdapterUserList? = null
    private var userViewModel: UserListViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUserListBinding.inflate(inflater, container, false)

        userViewModel = ViewModelProvider(this)[UserListViewModel::class.java]

        Log.d("TAG", "onCreateView:")

        userViewModel!!.getUser()
        userViewModel!!.userList.observe(viewLifecycleOwner) {
            binding.rvUser.layoutManager = LinearLayoutManager(requireContext())
            adapterUserList = AdapterUserList(it)
            binding.rvUser.adapter = adapterUserList
            adapterUserList!!.notifyDataSetChanged()
            Log.d("TAG", "onCreateView: $it")
        }
        userViewModel!!.message.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
        }





        return binding.root
    }

}