package com.example.ffbazaradmin.ui

import android.R
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.FragmentActivity
import com.google.android.material.textfield.TextInputEditText

class MethodsClass() {
    fun setSpinner(activity: FragmentActivity?, list: List<String>, spCatNames: Spinner) {
        val dataAdapter: ArrayAdapter<String> = ArrayAdapter(activity!!, R.layout.simple_spinner_item, list)
        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        spCatNames.adapter = dataAdapter
    }
     fun refreshAllView(imgCategoy: ImageView, imgSubCategory: ImageView,
                        txtCatName: TextInputEditText, txtSubCatName: TextInputEditText, txtDiamondNum: TextInputEditText,
                        txtPrice: TextInputEditText, txtStockOut: TextInputEditText, txtUidNeed: TextInputEditText, txtMemberShip: TextInputEditText) {
        imgCategoy.setImageURI(null)
        imgSubCategory.setImageURI(null)
        txtCatName.text = null
        txtSubCatName.text = null
        txtDiamondNum.text = null
        txtPrice.text = null
        txtStockOut.text = null
        txtUidNeed.text = null
        txtMemberShip.text = null
    }
}