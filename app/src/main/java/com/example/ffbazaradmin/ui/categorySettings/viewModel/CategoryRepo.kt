package com.example.ffbazaradmin.ui.categorySettings.viewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.ffbazaradmin.ui.adsSettings.Helper
import com.example.ffbazaradmin.ui.categorySettings.model.CategoryModel
import com.example.ffbazaradmin.ui.categorySettings.model.SubCategoryModel
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.text.SimpleDateFormat
import java.util.*

class CategoryRepo {
    private var category1NamesList: MutableList<String> = ArrayList()
    private val categoryReference = FirebaseDatabase.getInstance().reference.child(Helper.CATEGORY_PATH)
    fun saveToDatabase(
        categoryId: String?,
        tvCategoryName: String,
        imageUri: Uri?,
        s1: String,
        _message: MutableLiveData<String>,
        categoryKey: String?
    ) {
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)
        val imageRef: StorageReference =
            FirebaseStorage.getInstance().getReference("images/$fileName")

        imageRef.putFile(imageUri!!).addOnSuccessListener { taskSnapshot ->
            taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                var categoryModel = CategoryModel(
                    categoryId!!, tvCategoryName,
                    uri.toString(), s1
                )
                //  categoryList.add(categoryModel)
                saveData(categoryModel, _message, categoryKey)
            }
        }.addOnFailureListener { e ->
            _message.postValue(e.message)


        }
    }

    private fun saveData(
        categoryModel: CategoryModel,
        _message: MutableLiveData<String>,
        categoryKey: String?
    ) {
        FirebaseDatabase.getInstance().reference.child(Helper.CATEGORY_PATH).child(categoryKey!!)
            .setValue(categoryModel)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _message.postValue("Category Successfully Added")
                }
            }.addOnFailureListener {
                _message.postValue(it.message)

            }
    }


    fun getCategoryListSize(size: MutableLiveData<Int>, _message: MutableLiveData<String>) {
        categoryReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    size.postValue(snapshot.childrenCount.toInt())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                _message.postValue(error.message)
            }
        })
    }

    fun getCategoryType1List(liveData: MutableLiveData<List<String>>, _message: MutableLiveData<String>) {
        val query: Query =
            categoryReference.orderByChild(Helper.CATEGORY_TYPE_CHILD).equalTo("1")
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (snap in snapshot.children) {
                        var name = snap.child("name").value.toString()
                        if (name.isNotEmpty() || name != null) {
                            category1NamesList.add(name)
                            liveData.postValue(category1NamesList)
                        }
                    }
                } else {
                    _message.postValue("Child doesn't exist")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                _message.postValue(error.message)
            }
        })
    }


    fun getSubCategorySizeKey(catNames: String, keyList: MutableLiveData<List<Int>>, _message: MutableLiveData<String>) {
        val query: Query =
            categoryReference.orderByChild(Helper.CATEGORY_NAME).equalTo(catNames.trim())
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (snap in snapshot.children) {
                        var id = snap.child("id").value.toString()
                        getLastKet(id, keyList, _message)
                    }
                } else {
                    _message.postValue("Child doesn't exist")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                _message.postValue(error.message)
            }
        })
    }


    private fun getLastKet(id: String, keyList: MutableLiveData<List<Int>>, _message: MutableLiveData<String>) {
        val query: Query =
            categoryReference.child((id.toInt() - 1).toString()).child(Helper.SUB_CATEGORY)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (snap in snapshot.children) {
                        keyList.postValue(listOf(snap.key!!.toInt()))
                    }

                } else {
                    _message.postValue("list size 0")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                _message.postValue(error.message)
            }
        })
    }

    fun setSubCategoryData(subcategoryId: String?, subcategoryKey: Int, products: String,
                           tvSubCategoryName: String, imageUriSubCat: Uri?, _message: MutableLiveData<String>, categoryKey: String) {

        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)
        val imageRef: StorageReference =
            FirebaseStorage.getInstance().getReference("images/$fileName")

        imageRef.putFile(imageUriSubCat!!).addOnSuccessListener { taskSnapshot ->
            taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                Log.d("TAG", "setSubCategoryData: $subcategoryId")
                Log.d("TAG", "setSubCategoryData: $tvSubCategoryName")
                Log.d("TAG", "setSubCategoryData: $uri")
                var subCategoryModel = SubCategoryModel(subcategoryId!!, tvSubCategoryName, uri.toString(), "")

                saveToSubCategoryChild(subCategoryModel, subcategoryKey,categoryKey, _message)
            }
        }.addOnFailureListener { e ->
            _message.postValue(e.message)
        }
    }

    private fun saveToSubCategoryChild(subCategoryModel: SubCategoryModel, subcategoryKey: Int, categoryKey: String, _message: MutableLiveData<String>) {
        FirebaseDatabase.getInstance().reference.child(Helper.CATEGORY_PATH).child(categoryKey).child(Helper.SUB_CATEGORY).child(subcategoryKey.toString())
            .setValue(subCategoryModel)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _message.postValue("SubCategory Successfully Added")
                }
            }.addOnFailureListener {
                _message.postValue(it.message)

            }
    }


}