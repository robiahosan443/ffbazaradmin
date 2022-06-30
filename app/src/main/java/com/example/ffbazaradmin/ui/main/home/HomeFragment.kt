package com.example.ffbazaradmin.ui.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.ffbazaradmin.R
import com.example.ffbazaradmin.databinding.FragmentAdsIdBinding
import com.example.ffbazaradmin.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val TAG = "HomeFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.cardAdsSettings.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_AdsIdFragment)
        }
        binding.cardPaymentList.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_PaymentRequestFragment)
        }
        binding.cardUserList.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_userListFragment)
        }
        binding.cardCategorySettings.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_categorySettingsFragment)
        }

        return binding.root
    }


}