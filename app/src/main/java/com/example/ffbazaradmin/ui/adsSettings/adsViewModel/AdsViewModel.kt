package com.example.ffbazaradmin.ui.adsSettings.adsViewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.ffbazaradmin.ui.adsSettings.adsRepository.AdsRepository
import com.example.ffbazaradmin.ui.adsSettings.model.AdsModel

class AdsViewModel : ViewModel() {
    private val rapo = AdsRepository()

    private var _getAdsMutableLiveData = MutableLiveData<AdsModel>()
    var getAdsLiveData: LiveData<AdsModel> = _getAdsMutableLiveData


    private var _message = MutableLiveData<String>()
    var message: LiveData<String> = _message

    fun getAdsData() {
        rapo.getAdsData(_getAdsMutableLiveData)
    }

    fun setAds(
        adsType: String,
        admobBannerId: String,
        admobIntersId: String,
        fanBannerId: String,
        fanIntersId: String,
        interval: String,
        ironSourceAppKey: String,
        unityBannerId: String,
        unityGameId: String,
        unityIntersId: String
    ) {


        rapo.setAds(
            adsType,
            admobBannerId,
            admobIntersId,
            fanBannerId,
            fanIntersId,
            interval,
            ironSourceAppKey,
            unityBannerId,
            unityGameId,
            unityIntersId,
            _message
        )

        Log.d("TAG", "rapo: ")
    }
}