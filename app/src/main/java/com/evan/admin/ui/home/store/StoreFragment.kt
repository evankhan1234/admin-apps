package com.evan.admin.ui.home.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.evan.admin.R
import com.evan.admin.ui.home.HomeActivity


class StoreFragment : Fragment() {
    var linear_post: LinearLayout?=null
    var linear_inactive_shop: LinearLayout?=null
    var linear_active_shop: LinearLayout?=null
    var linear_product: LinearLayout?=null
    var linear_customer: LinearLayout?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_store, container, false)
        linear_post=root?.findViewById(R.id.linear_post)
        linear_inactive_shop=root?.findViewById(R.id.linear_inactive_shop)
        linear_active_shop=root?.findViewById(R.id.linear_active_shop)
        linear_product=root?.findViewById(R.id.linear_product)
        linear_customer=root?.findViewById(R.id.linear_customer)
        linear_post?.setOnClickListener {
            if (activity is HomeActivity) {
                (activity as HomeActivity).goToInactivePostFragment()
            }
        }
        linear_inactive_shop?.setOnClickListener {
            if (activity is HomeActivity) {
                (activity as HomeActivity).goToInactiveShopFragment()
            }
        }
        linear_active_shop?.setOnClickListener {
            if (activity is HomeActivity) {
                (activity as HomeActivity).goToActiveShopFragment()
            }
        }
        linear_product?.setOnClickListener {
            if (activity is HomeActivity) {
                (activity as HomeActivity).goToProductFragment()
            }
        }
        linear_customer?.setOnClickListener {
            if (activity is HomeActivity) {
                (activity as HomeActivity).goToCustomerFragment()
            }
        }
        return root;
    }


}