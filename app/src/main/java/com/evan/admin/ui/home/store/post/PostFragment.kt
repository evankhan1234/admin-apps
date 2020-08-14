package com.evan.admin.ui.home.store.post

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
import com.evan.admin.data.db.entities.Post
import com.evan.admin.ui.home.HomeActivity
import com.evan.admin.util.NetworkState
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class PostFragment : Fragment() ,KodeinAware,IPostUpdateListener{
    override val kodein by kodein()

    private val factory : PostModelFactory by instance()
    private lateinit var viewModel: PostViewModel

    var rcv_post: RecyclerView?=null
    var postAdapter:PostAdapter?=null
    var progress_bar: ProgressBar?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_post, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(PostViewModel::class.java)
        rcv_post=root?.findViewById(R.id.rcv_post)
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
        postAdapter = PostAdapter(context!!,this)
        rcv_post?.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rcv_post?.adapter = postAdapter
        startListening()
    }

    private fun startListening() {

        viewModel.listOfAlerts?.observe(this, Observer {
            postAdapter?.submitList(it)
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

    override fun onUpdate(post: Post) {

        if ( activity is HomeActivity){
            (activity as HomeActivity).goToViewInActiveFragment(post)
        }
    }
}