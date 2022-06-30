package com.example.ffbazaradmin.ui.adsSettings.adsRepository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.ffbazaradmin.ui.adsSettings.model.AdsModel
import com.google.firebase.database.*

class AdsRepository {

    private val TAG = "AdsRepository"
    private var adsReference: DatabaseReference =
        FirebaseDatabase.getInstance().reference.child("ads")


    fun getAdsData(liveData: MutableLiveData<AdsModel>) {

        adsReference.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        var model = snapshot.getValue(AdsModel::class.java)
                        if (model != null) {
                            //adsModel.add(model)
                            liveData.postValue(model)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG, "onCancelled: ${error.message}")
                }
            })
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
        unityIntersId: String,
        _message: MutableLiveData<String>
    ) {
        val map: MutableMap<String, Any> = HashMap()
        map["admobBannerId"] = admobBannerId
        map["admobIntersId"] = admobIntersId
        map["fanBannerId"] = fanBannerId
        map["fanIntersId"] = fanIntersId
        map["interval"] = interval
        map["ironSourceAppKey"] = ironSourceAppKey
        map["selectedAd"] = adsType
        map["unityBannerId"] = unityBannerId
        map["unityGameId"] = unityGameId
        map["unityIntersId"] = unityIntersId

        adsReference.updateChildren(map).addOnCompleteListener {
            if (it.isSuccessful) {
                _message.postValue("Ads saved")
                Log.d(TAG, "setAds: ")
            }
        }.addOnFailureListener {
            _message.postValue(it.message.toString())

        }
    }
}