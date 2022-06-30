package com.example.ffbazaradmin.ui.userList.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ffbazaradmin.R
import com.example.ffbazaradmin.ui.paymentRequests.model.PaymentRequestModel
import com.example.ffbazaradmin.ui.userList.model.UserModel
import de.hdodenhof.circleimageview.CircleImageView

class AdapterUserList(private var userList: List<UserModel>) :
    RecyclerView.Adapter<AdapterUserList.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_list, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txt_balance.text = userList[position].points + " ৳"
        holder.txt_userId.text = userList[position].userId
        holder.txt_name.text = userList[position].name
        holder.txt_email.text = userList[position].email
        holder.txt_phone.text = userList[position].phone
        holder.txt_address.text = userList[position].address
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txt_balance: TextView = itemView.findViewById(R.id.txt_balance)
        var txt_userId: TextView = itemView.findViewById(R.id.txt_userId)
        var txt_name: TextView = itemView.findViewById(R.id.txt_name)
        var txt_email: TextView = itemView.findViewById(R.id.txt_email)
        var txt_phone: TextView = itemView.findViewById(R.id.txt_phone)
        var txt_address: TextView = itemView.findViewById(R.id.txt_address)

    }
}




