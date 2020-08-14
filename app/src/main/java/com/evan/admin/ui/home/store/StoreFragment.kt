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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_store, container, false)
        linear_post=root?.findViewById(R.id.linear_post)
        linear_post?.setOnClickListener {
            if (activity is HomeActivity) {
                (activity as HomeActivity).goToInactivePostFragment()
            }
        }
        return root;
    }


}