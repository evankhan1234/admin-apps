package com.evan.dokan.ui.home.notice

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.evan.admin.R
import com.evan.admin.data.db.entities.Notice
import com.evan.admin.ui.home.HomeActivity
import com.evan.admin.ui.home.notice.INoticeUpdateListener
import com.evan.admin.ui.home.notice.NoticeModelFactory
import com.evan.admin.ui.home.notice.NoticeViewFragment
import com.evan.admin.ui.home.notice.NoticeViewModel

import com.evan.admin.util.NetworkState
import com.evan.admin.util.notice_update

import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class NoticeFragment : Fragment(),KodeinAware, INoticeUpdateListener {
    override val kodein by kodein()

    private val factory : NoticeModelFactory by instance()

    var noticeAdapter:NoticeAdapter?=null


    private lateinit var viewModel: NoticeViewModel

    var rcv_notice: RecyclerView?=null
    var btn_notice_new: ImageView?=null
    var progress_bar: ProgressBar?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_notice, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(NoticeViewModel::class.java)
        rcv_notice=root?.findViewById(R.id.rcv_notice)
        btn_notice_new=root?.findViewById(R.id.btn_notice_new)
        progress_bar=root?.findViewById(R.id.progress_bar)

        btn_notice_new?.setOnClickListener {
            (activity as HomeActivity).goToNoticeAddFragment()
            notice_update="Add"
        }
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
        noticeAdapter = NoticeAdapter(context!!,this)
        rcv_notice?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        rcv_notice?.adapter = noticeAdapter
        startListening()

    }

    private fun startListening() {


        viewModel.listOfAlerts?.observe(this, Observer {
            noticeAdapter?.submitList(it)
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

    override fun onUpdate(notice: Notice) {
        if (activity is HomeActivity) {
            (activity as HomeActivity).goToNoticeDetailsFragment(notice)
            notice_update="Update"
        }
    }
    fun removeChild() {
        val f =
            childFragmentManager.findFragmentByTag(NoticeViewFragment::class.java.simpleName)
        val f1 = childFragmentManager.findFragmentByTag(NoticeViewFragment::class.java.simpleName)
        if (f != null) {
            val transaction = childFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.left_to_right, R.anim.left_to_right)
            transaction.remove(f)
            transaction.commit()
            childFragmentManager.popBackStack()
        }
    }
}