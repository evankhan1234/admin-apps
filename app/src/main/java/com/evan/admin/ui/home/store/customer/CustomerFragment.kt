package com.evan.admin.ui.home.store.customer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.evan.admin.R
import com.evan.admin.data.db.entities.Customer
import com.evan.admin.data.db.entities.Post
import com.evan.admin.ui.home.HomeActivity
import com.evan.admin.ui.home.store.post.IPostUpdateListener
import com.evan.admin.ui.home.store.post.PostAdapter
import com.evan.admin.ui.home.store.post.PostModelFactory
import com.evan.admin.ui.home.store.post.PostViewModel
import com.evan.admin.util.NetworkState
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class CustomerFragment : Fragment() , KodeinAware, ICustomerUpdateListener {
    override val kodein by kodein()

    private val factory : CustomerModelFactory by instance()
    private lateinit var viewModel: CustomerViewModel

    var rcv_customer: RecyclerView?=null
    var customerAdapter: CustomerAdapter?=null
    var progress_bar: ProgressBar?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_customer, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(CustomerViewModel::class.java)
        rcv_customer=root?.findViewById(R.id.rcv_customer)
        progress_bar=root?.findViewById(R.id.progress_bar)
        return root
    }

    fun replace(){
        viewModel.replaceSubscription(this)
        startListening()
    }
    override fun onResume() {
        super.onResume()
        // viewModel.getCategoryType(token!!)
        Log.e("stop","stop")
        replace()
        initAdapter()
        initState()
    }

    private fun initAdapter() {
        customerAdapter = CustomerAdapter(context!!,this)
        rcv_customer?.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rcv_customer?.adapter = customerAdapter
        startListening()
    }

    private fun startListening() {

        viewModel.listOfAlerts?.observe(this, Observer {
            customerAdapter?.submitList(it)
        })

    }


    private fun initState() {
        viewModel.getNetworkState().observe(this, Observer { state ->
            when (state.status) {
                NetworkState.Status.LOADIND -> {
                    progress_bar?.visibility=View.VISIBLE
                }
                NetworkState.Status.SUCCESS -> {
                    progress_bar?.visibility=View.GONE
                }
                NetworkState.Status.FAILED -> {
                    progress_bar?.visibility=View.GONE
                }
            }
        })
    }

    override fun onUpdate(customer: Customer) {
        if ( activity is HomeActivity){
            (activity as HomeActivity).goToViewCustomerFragment(customer)
        }
    }
}