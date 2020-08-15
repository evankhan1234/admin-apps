package com.evan.admin.ui.home.store.inactive_shop

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.evan.admin.R
import com.evan.admin.data.db.entities.Shop
import com.google.gson.Gson
import kotlinx.android.synthetic.main.layout_inactive_shop_list.view.*
import java.text.SimpleDateFormat

class InactiveShopAdapter (val context: Context, val shop: MutableList<Shop>?, val shopUpdateListener: IShopUpdateListener) : RecyclerView.Adapter<InactiveShopAdapter.CustomViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val cellForRow = inflater.inflate(R.layout.layout_inactive_shop_list, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        Log.e("getList",""+ Gson().toJson(shop));

        return shop!!.size
    }

    override fun onBindViewHolder(holder:CustomViewHolder, position: Int) {


        holder.itemView.text_update.setOnClickListener {
            shopUpdateListener.onUpdate(shop?.get(position)!!)
        }
        Glide.with(context)
            .load(shop?.get(position)?.Picture)
            .into(holder.itemView.img_icon!!)
        holder.itemView.text_name.text=shop?.get(position)?.ShopName
        holder.itemView.text_phone_number.text=shop?.get(position)?.OwnerMobileNumber
        holder.itemView.text_email.text=shop?.get(position)?.Email
        holder.itemView.text_address.text=shop?.get(position)?.Address
        holder.itemView.tv_date.text=getStartDate(shop?.get(position)?.AgreementDate)



    }
    fun getStartDate(startDate: String?): String? {
        val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val formatter = SimpleDateFormat("dd,MMMM yyyy")
        val output: String = formatter.format(parser.parse(startDate!!))
        return output
    }
    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }
}