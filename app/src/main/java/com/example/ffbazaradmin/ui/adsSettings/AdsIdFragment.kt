package com.example.ffbazaradmin.ui.adsSettings

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.ffbazaradmin.databinding.FragmentAdsIdBinding
import com.example.ffbazaradmin.ui.adsSettings.adsViewModel.AdsViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class AdsIdFragment : Fragment() {

    private var _binding: FragmentAdsIdBinding? = null
    private val binding get() = _binding!!
    private val TAG = "AdsIdFragment"
    private var adsType: String? = null

    private var admobBannerId: String? = null
    private var admobIntersId: String? = null
    private var fanBannerId: String? = null
    private var fanIntersId: String? = null
    private var interval: String? = null
    private var ironSourceAppKey: String? = null
    private var unityBannerId: String? = null
    private var unityGameId: String? = null
    private var unityIntersId: String? = null
    private var viewModel: AdsViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdsIdBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[AdsViewModel::class.java]

        getAds()
        binding.btnSave.setOnClickListener {
            adsType = binding.spAdsType.selectedItem.toString()
            admobBannerId = binding.admobBannerId.text.toString()
            admobIntersId = binding.admobIntersId.text.toString()
            fanBannerId = binding.fanBannerId.text.toString()
            fanIntersId = binding.fanIntersId.text.toString()
            interval = binding.interval.text.toString()
            ironSourceAppKey = binding.ironSourceAppKey.text.toString()
            unityBannerId = binding.unityBannerId.text.toString()
            unityGameId = binding.unityGameId.text.toString()
            unityIntersId = binding.unityIntersId.text.toString()

            if (admobBannerId!!.isEmpty()) {
                binding.tilBanner.error = "Invalid"
            } else if (admobIntersId!!.isEmpty()) {
                binding.tiladmobIntersId.error = "Invalid"
            } else if (fanBannerId!!.isEmpty()) {
                binding.tilFanBannerId.error = "Invalid"
            } else if (fanIntersId!!.isEmpty()) {
                binding.tilFanIntersId.error = "Invalid"
            } else if (interval!!.isEmpty()) {
                binding.tilInterval.error = "Invalid"
            } else if (ironSourceAppKey!!.isEmpty()) {
                binding.tilIronSourceAppKey.error = "Invalid"
            } else if (unityBannerId!!.isEmpty()) {
                binding.tilUnityBannerId.error = "Invalid"
            } else if (unityGameId!!.isEmpty()) {
                binding.tilUnityGameId.error = "Invalid"
            } else if (unityIntersId!!.isEmpty()) {
                binding.tilUnityIntersId.error = "Invalid"
            } else {
                viewModel!!.setAds(
                    adsType!!,
                    admobBannerId!!,
                    admobIntersId!!,
                    fanBannerId!!,
                    fanIntersId!!,
                    interval!!, ironSourceAppKey!!, unityBannerId!!, unityGameId!!,
                    unityIntersId!!
                )
            }
        }

        viewModel!!.message.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
            Log.d(TAG, "onCreateView:$it")
        }

        return binding.root
    }

    private fun getAds() {
        viewModel!!.getAdsData()
        viewModel!!.getAdsLiveData.observe(viewLifecycleOwner) { model ->
            binding.admobBannerId.setText(model.admobBannerId)
            binding.admobIntersId.setText(model.admobIntersId)
            binding.fanBannerId.setText(model.fanBannerId)
            binding.fanIntersId.setText(model.fanIntersId)
            binding.interval.setText(model.interval)
            binding.ironSourceAppKey.setText(model.ironSourceAppKey)
            binding.unityBannerId.setText(model.unityBannerId)
            binding.unityGameId.setText(model.unityGameId)
            binding.unityIntersId.setText(model.unityIntersId)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}