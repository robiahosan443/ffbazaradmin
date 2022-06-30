package com.example.ffbazaradmin.ui.paymentRequests.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ffbazaradmin.R
import com.example.ffbazaradmin.ui.paymentRequests.model.PaymentRequestModel
import de.hdodenhof.circleimageview.CircleImageView

class PaymentRequestAdapter(private var paymentRequestList: List<PaymentRequestModel>) :
    RecyclerView.Adapter<PaymentRequestAdapter.ViewHolder>() {


    var onItemClick: ((paymentRequestList: List<PaymentRequestModel>, pos: Int) -> Unit)? = null

    var onApproveClick: ((edtUid: String, paymentRequestList: List<PaymentRequestModel>, pos: Int) -> Unit)? =
        null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_request_without_uid, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_price.text = "৳" + paymentRequestList[position].price
        if (paymentRequestList[position].diamondnum.isEmpty()) {
            holder.tv_subcategory.text =
                paymentRequestList[position].subCategory + "(Membership Request)"
        } else {
            holder.tv_subcategory.text =
                paymentRequestList[position].subCategory + "(" + paymentRequestList[position].diamondnum + ")"
        }

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(paymentRequestList, position)


        }
    }

    override fun getItemCount(): Int {
        return paymentRequestList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_subcategory: TextView = itemView.findViewById(R.id.tv_subcategory)
        var tv_diamond_num: TextView = itemView.findViewById(R.id.tv_diamond_num)
        var tv_price: TextView = itemView.findViewById(R.id.tv_price)
        var payment_image: CircleImageView = itemView.findViewById(R.id.payment_image)

    }
}




