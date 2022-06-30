package com.example.ffbazaradmin.ui.categorySettings

import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ffbazaradmin.R
import com.example.ffbazaradmin.databinding.FragmentCategorySettingsBinding
import com.example.ffbazaradmin.ui.MethodsClass
import com.example.ffbazaradmin.ui.categorySettings.repository.CategoryViewModel


class CategorySettingsFragment : Fragment() {

    private var _binding: FragmentCategorySettingsBinding? = null
    private val binding get() = _binding!!
    private var TAG = "CategorySettingsFragment"
    private var tvCategoryName: String? = null
    private var tvSubCategoryName: String? = null
    private var imageUriCat: Uri? = null
    private var imageUriSubCat: Uri? = null
    private var viewModel: CategoryViewModel? = null
    private var categoryId: String? = null
    private var categoryKey = "0"
    private var subcategoryId: String? = null
    private var subcategoryKey = 0
    private var tvAddSubCategory: String? = null
    private var tvAddProduct: String? = null
    private var progressDialog: ProgressDialog? = null
    private var methodsClass = MethodsClass()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCategorySettingsBinding.inflate(inflater, container, false)

        progressDialog = ProgressDialog(activity)
        progressDialog!!.setTitle("Warning...")
        progressDialog!!.setMessage("Application is loading, please wait")

        viewModel = ViewModelProvider(this)[CategoryViewModel::class.java]
        viewModel!!.getCategoryListSize()
        viewModel!!.sizeCategoryList.observe(viewLifecycleOwner) {
            categoryKey = (it).toString()
            categoryId = (it + 1).toString()
        }
        viewModel!!.getCategoryType1NameList()
        viewModel!!.category1NamesList.observe(viewLifecycleOwner) {
            methodsClass.setSpinner(activity, it, binding.spCatNames)
        }


        setCategorySpinner()
        setBtnListener()
        getCatImage()
        getSubCategoryImage()


        return binding.root
    }

    private fun setBtnListener() {
        binding.btnUpload.setOnClickListener {
            //  progressDialog!!.show()

            if (binding.spChild.selectedItemPosition == 1 && binding.spCategory.selectedItemPosition == 1 && tvAddSubCategory.equals(null)) {
                tvCategoryName = binding.txtCatName.text.toString()
                if (tvCategoryName!!.isEmpty() || binding.imgCategoy.drawable == null || imageUriCat.toString().isEmpty()) {
                    Toast.makeText(activity, "Please Enter Category name and image", Toast.LENGTH_SHORT).show()
                    progressDialog!!.dismiss()
                } else {
                    viewModel!!.categoryData(categoryId, tvCategoryName!!, imageUriCat, "1", categoryKey)
                    viewModel!!.message.observe(viewLifecycleOwner) {
                        Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
                        progressDialog!!.dismiss()
                    }
                }
            } else if (binding.spChild.selectedItemPosition == 1 && binding.spCategory.selectedItemPosition == 1 &&
                tvAddSubCategory!!.isNotEmpty() && tvAddProduct.equals(null)
            ) {
                tvSubCategoryName = binding.txtSubCatName.text.toString()
                tvCategoryName = binding.txtCatName.text.toString()
                if (tvCategoryName!!.isEmpty() || binding.imgCategoy.drawable == null || imageUriCat.toString().isEmpty()) {
                    Toast.makeText(activity, "Please Enter Category name and image", Toast.LENGTH_SHORT).show()
                } else if (tvSubCategoryName!!.isEmpty() || binding.imgSubCategory.drawable == null || imageUriSubCat.toString().isEmpty()) {
                    Toast.makeText(activity, "Please Enter SubCategory name and image", Toast.LENGTH_SHORT).show()
                    progressDialog!!.dismiss()
                } else {
                    subcategoryId = (subcategoryKey + 1).toString()
                    viewModel!!.categoryData(categoryId, tvCategoryName!!, imageUriCat, "1", categoryKey)

                    viewModel!!.setSubCategoryData(subcategoryId, subcategoryKey, "products", tvSubCategoryName!!, imageUriSubCat,categoryKey)
                    viewModel!!.message.observe(viewLifecycleOwner) {
                        Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
                        //   progressDialog!!.dismiss()
                    }


                }
            }
        }

        binding.tvAddSubCategory.setOnClickListener {
            tvAddSubCategory = binding.tvAddSubCategory.text.toString()
            binding.tvAddSubCategory.visibility = View.GONE
            binding.layoutAddSubCategory.visibility = View.VISIBLE
        }
        binding.tvAddProduct.setOnClickListener {
            tvAddProduct = binding.tvAddProduct.text.toString()
            binding.tvAddProduct.visibility = View.GONE
            binding.layoutAddProduct.visibility = View.VISIBLE
        }
    }

    private fun setCategorySpinner() {
        binding.spCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                refreshLayout()
                val selectedItem = parent.getItemAtPosition(position).toString()
                if (selectedItem == "category_type 1") {
                    binding.cardCatType1.visibility = View.VISIBLE
                    setSpAdapter(selectedItem)

                } else if (selectedItem == "category_type 2") {
                    binding.cardCatType1.visibility = View.VISIBLE
                    setSpAdapter(selectedItem)
                } else {
                    binding.cardCatType1.visibility = View.GONE
                    if (binding.layoutAddCategory.visibility == View.VISIBLE ||
                        binding.layoutAddCategory.visibility == View.VISIBLE ||
                        binding.layoutAddProduct.visibility == View.VISIBLE
                    ) {
                        binding.layoutAddCategory.visibility = View.GONE
                        binding.layoutAddSubCategory.visibility = View.GONE
                        binding.layoutAddProduct.visibility = View.GONE
                    }
                    binding.btnUpload.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d(TAG, "onNothingSelected: ")
            }
        }
    }

    private fun setSpAdapter(tvCategoryTypePos: String) {
        binding.btnUpload.visibility = View.VISIBLE
        if (tvCategoryTypePos == "category_type 1") {
            ArrayAdapter.createFromResource(activity!!, R.array.child, android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spChild.adapter = adapter
            }
            binding.spChild.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    refreshLayout()
                    Log.d(TAG, "onItemSelected: $position")
                    if (position == 1) {
                        binding.layoutAddCategory.visibility = View.VISIBLE
                        binding.layoutAddSubCategory.visibility = View.GONE
                        binding.layoutAddProduct.visibility = View.GONE
                        binding.cardCatNames.visibility = View.GONE
                        binding.tvAddSubCategory.visibility = View.VISIBLE
                    } else if (position == 2) {
                        binding.layoutAddSubCategory.visibility = View.VISIBLE
                        binding.cardCatNames.visibility = View.VISIBLE
                        binding.layoutAddCategory.visibility = View.GONE
                        binding.layoutAddProduct.visibility = View.GONE
                    } else if (position == 3) {
                        binding.layoutAddProduct.visibility = View.VISIBLE
                        binding.layoutAddCategory.visibility = View.GONE
                        binding.layoutAddSubCategory.visibility = View.GONE
                        binding.cardCatNames.visibility = View.GONE
                    } else {
                        binding.layoutAddCategory.visibility = View.GONE
                        binding.layoutAddSubCategory.visibility = View.GONE
                        binding.layoutAddProduct.visibility = View.GONE
                        binding.cardCatNames.visibility = View.GONE
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }

        } else {
            ArrayAdapter.createFromResource(activity!!, R.array.child_type_2, android.R.layout.simple_spinner_item).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spChild.adapter = adapter
            }
            binding.spChild.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                    if (position == 1) {
                        binding.layoutAddSubCategory.visibility = View.VISIBLE
                        binding.layoutAddProduct.visibility = View.GONE
                    } else if (position == 2) {
                        binding.layoutAddProduct.visibility = View.VISIBLE
                        binding.layoutAddSubCategory.visibility = View.GONE
                    } else {
                        binding.layoutAddSubCategory.visibility = View.GONE
                        binding.layoutAddProduct.visibility = View.GONE
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        }
    }

    private fun refreshLayout() {
        methodsClass.refreshAllView(binding.imgCategoy, binding.imgSubCategory, binding.txtCatName,
            binding.txtSubCatName, binding.txtDiamondNum, binding.txtPrice, binding.txtStockOut, binding.txtUidNeed, binding.txtMemberShip)
        tvAddSubCategory = null
        tvAddProduct = null
    }

    private fun getCatImage() {
        binding.cardCatImage.setOnClickListener {
            val intent = Intent()
            intent.type = "image/"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityIntent.launch(intent)
        }
    }

    private fun getSubCategoryImage() {
        binding.cardSubCatImage.setOnClickListener {
            val intent = Intent()
            intent.type = "image/"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityIntentForSubCategory.launch(intent)
        }
    }

    private var startActivityIntent = registerForActivityResult(
        StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            // Here, no request code
            val data = result.data
            if (data != null) {
                if (binding.imgCategoy.visibility == View.VISIBLE) {
                    imageUriCat = data.data!!
                    binding.imgCategoy.setImageURI(imageUriCat)
                }

            }
        }
    }

    private var startActivityIntentForSubCategory = registerForActivityResult(
        StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            // Here, no request code
            val data = result.data
            if (data != null) {
                imageUriSubCat = data.data!!
                binding.imgSubCategory.setImageURI(imageUriSubCat)
            }
        }
    }


}