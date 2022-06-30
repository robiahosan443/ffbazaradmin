package com.example.ffbazaradmin.ui.categorySettings.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ffbazaradmin.ui.categorySettings.viewModel.CategoryRepo

class CategoryViewModel : ViewModel() {
    private var repo = CategoryRepo()

    private var _message = MutableLiveData<String>()
    var message: LiveData<String> = _message

    private var _sizeCategoryList = MutableLiveData<Int>()
    var sizeCategoryList: LiveData<Int> = _sizeCategoryList

    private var _sizeSubCategoryKeyList = MutableLiveData<List<Int>>()
    var sizeSubCategoryKeyList: LiveData<List<Int>> = _sizeSubCategoryKeyList

    private var _category1NamesList = MutableLiveData<List<String>>()
    var category1NamesList: LiveData<List<String>> = _category1NamesList

    fun categoryData(
        categoryId: String?,
        tvCategoryName: String,
        imageUri: Uri?,
        s1: String,
        categoryKey: String?
    ) {
        repo.saveToDatabase(categoryId, tvCategoryName, imageUri, s1, _message, categoryKey)
    }


    fun getCategoryListSize() {
        repo.getCategoryListSize(_sizeCategoryList, _message)
    }

    fun getCategoryType1NameList() {
        repo.getCategoryType1List(_category1NamesList, _message)
    }


    fun getSubCategorySizeKey(catNames: String) {
        repo.getSubCategorySizeKey(catNames, _sizeSubCategoryKeyList, _message)

    }

    fun setSubCategoryData(subcategoryId: String?, subcategoryKey: Int, products: String, tvSubCategoryName: String,
                           imageUriSubCat: Uri?,
                           categoryKey: String) {

        repo.setSubCategoryData(subcategoryId, subcategoryKey, products, tvSubCategoryName, imageUriSubCat,_message,categoryKey)
    }
}