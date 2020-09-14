package com.evan.admin.ui.home.store.customer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.evan.admin.R
import com.evan.admin.data.db.entities.Customer
import com.evan.admin.data.db.entities.Shop
import com.evan.admin.ui.home.HomeActivity
import com.evan.admin.ui.home.HomeViewModel
import com.evan.admin.ui.home.HomeViewModelFactory
import com.evan.admin.ui.home.store.inactive_shop.IShopSuccessListener
import com.evan.admin.util.SharedPreferenceUtil
import com.google.gson.Gson
import de.hdodenhof.circleimageview.CircleImageView
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat


class CustomerViewFragment : Fragment() , KodeinAware, ICustomerSuccessListener {
    override val kodein by kodein()

    private val factory : CustomerModelFactory by instance()
    private lateinit var viewModel: CustomerViewModel
    var progress_bar: ProgressBar?=null
    var tv_name: TextView?=null
    var tv_email: TextView?=null
    var tv_mobile: TextView?=null
    var tv_date: TextView?=null
    var tv_shop_address: TextView?=null
    var customer: Customer?=null
    var img_avatar: CircleImageView?=null
    var btn_done: Button?=null
    var token:String?=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_customer_view, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(CustomerViewModel::class.java)
        viewModel?.customerSuccessListener=this
        progress_bar=root.findViewById(R.id.progress_circular)
        tv_name=root.findViewById(R.id.tv_name)
        tv_email=root.findViewById(R.id.tv_email)
        img_avatar=root.findViewById(R.id.img_avatar)
        tv_mobile=root.findViewById(R.id.tv_mobile)
        tv_date=root.findViewById(R.id.tv_date)

        tv_shop_address=root.findViewById(R.id.tv_shop_address)
        btn_done=root.findViewById(R.id.btn_done)
        token = SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)
        val args: Bundle? = arguments
        if (args != null) {
            if (args?.containsKey(Customer::class.java.getSimpleName()) != null) {
                customer = args?.getParcelable(Customer::class.java.getSimpleName())
                Log.e("data", "data" + Gson().toJson(customer!!))
            }
        }
        init()
        if (customer!!.Status==1){
            btn_done?.setText("Decline")
        }
        else{
            btn_done?.setText("Approve")
        }
        btn_done?.setOnClickListener {

            if (customer!!.Status==1){

                viewModel?.updateCustomer(token!!,customer?.Id!!,0)
            }
            else{
                viewModel?.updateCustomer(token!!,customer?.Id!!,1)
            }

        }
        return root
    }
    fun init(){
        tv_name?.text=customer?.Name
        tv_email?.text=customer?.Email
        tv_mobile?.text=customer?.MobileNumber
        tv_shop_address?.text=customer?.Address
        tv_date?.text=getStartDate(customer?.Created)
        Glide.with(context!!)
            .load(customer?.Picture)
            .into(img_avatar!!)
    }
    fun getStartDate(startDate: String?): String? {
        val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val formatter = SimpleDateFormat("dd,MMMM yyyy")
        val output: String = formatter.format(parser.parse(startDate!!))
        return output
    }
    override fun onStarted() {
        progress_bar?.visibility=View.VISIBLE
    }

    override fun onEnd() {
        progress_bar?.visibility=View.GONE
    }

    override fun onSuccess(message: String) {
        Toast.makeText(activity!!,message, Toast.LENGTH_SHORT).show()
        if ( activity is HomeActivity){
            (activity as HomeActivity).onBackPressed()
        }
    }

    override fun onFailure(message: String) {
        Toast.makeText(activity!!,message, Toast.LENGTH_SHORT).show()
    }


}