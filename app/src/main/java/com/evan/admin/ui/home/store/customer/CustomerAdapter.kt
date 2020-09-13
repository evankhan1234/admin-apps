package com.evan.admin.ui.home.store.customer

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.evan.admin.R
import com.evan.admin.data.db.entities.Customer
import com.evan.admin.ui.home.store.post.IPostUpdateListener
import kotlinx.android.synthetic.main.layout_customer_list.view.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class CustomerAdapter (val context: Context, val updateListener: ICustomerUpdateListener) :
    PagedListAdapter<Customer, RecyclerView.ViewHolder>(NewsDiffCallback) {
    private val DATA_VIEW_TYPE = 1
    private val FOOTER_VIEW_TYPE = 2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AlertViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE)
            (holder as AlertViewHolder).bind(
                context,
                getItem(position),
                position,
                updateListener
            )
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    companion object {
        val NewsDiffCallback = object : DiffUtil.ItemCallback<Customer>() {
            override fun areItemsTheSame(oldItem: Customer, newItem: Customer): Boolean {
                return oldItem.Id == newItem.Id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Customer, newItem: Customer): Boolean {
                return oldItem == newItem
            }
        }
    }

}

class AlertViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(context: Context, customer: Customer?, position: Int, listener: ICustomerUpdateListener) {

        if (customer != null) {

            itemView.text_update.setOnClickListener {
                listener.onUpdate(customer)
            }
            Glide.with(context)
                .load(customer?.Picture)
                .into(itemView.img_icon!!)
            itemView.text_name.text=customer?.Name
            itemView.text_phone_number.text=customer?.MobileNumber
            itemView.text_email.text=customer?.Email
            itemView.text_address.text=customer?.Address
            itemView.tv_date.text=getStartDate(customer?.Created)
            if (customer?.Status==1){
                itemView.text_active.text="Active"
                itemView.text_active?.setTextColor(context?.resources.getColor(R.color.green))
            }
            else{
                itemView.text_active.text="Inactive"
                itemView.text_active?.setTextColor(context?.resources.getColor(R.color.red_marker))

            }

        }
    }

    companion object {
        fun create(parent: ViewGroup): AlertViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_customer_list, parent, false)

            return AlertViewHolder(view)
        }
    }
    fun getStartDate(startDate: String?): String? {
        val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val formatter = SimpleDateFormat("dd,MMMM yyyy")
        val output: String = formatter.format(parser.parse(startDate!!))
        return output
    }

}