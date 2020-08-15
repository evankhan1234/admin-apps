package com.evan.admin.ui.home.store.inactive_shop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.evan.admin.R
import com.evan.admin.data.db.entities.Shop
import com.evan.admin.ui.home.HomeActivity
import com.evan.admin.ui.home.HomeViewModel
import com.evan.admin.ui.home.HomeViewModelFactory
import com.evan.admin.ui.home.store.post.IPostUpdateListener
import com.evan.admin.ui.home.store.post.PostAdapter
import com.evan.admin.ui.home.store.post.PostModelFactory
import com.evan.admin.ui.home.store.post.PostViewModel
import com.evan.admin.util.SharedPreferenceUtil
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class InactiveShopFragment : Fragment() , KodeinAware, IShopUpdateListener,IShopListListener {
    override val kodein by kodein()

    private val factory : HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel

    var rcv_shop: RecyclerView?=null
    var inactiveShopAdapter: InactiveShopAdapter?=null
    var progress_bar: ProgressBar?=null

    var token:String?=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_inactive_shop, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        viewModel?.shopListListener=this
        rcv_shop=root.findViewById(R.id.rcv_shop)
        progress_bar=root.findViewById(R.id.progress_bar)
        token = SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)
        return root
    }
    override fun onResume() {
        super.onResume()
        viewModel.getShops(token!!)
    }

    override fun onUpdate(shop: Shop) {
        if ( activity is HomeActivity){
            (activity as HomeActivity).goToViewInActiveShopFragment(shop)
        }
    }

    override fun shop(data: MutableList<Shop>?) {
        inactiveShopAdapter = InactiveShopAdapter(context!!,data!!,this)
        rcv_shop?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
            adapter = inactiveShopAdapter
        }
    }

    override fun onStarted() {
        progress_bar?.visibility=View.VISIBLE
    }

    override fun onEnd() {
        progress_bar?.visibility=View.GONE
    }

    override fun onFailure(message: String) {
        Toast.makeText(activity!!,message, Toast.LENGTH_SHORT).show()
    }


}