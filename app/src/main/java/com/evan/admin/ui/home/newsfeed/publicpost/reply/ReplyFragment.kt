package com.evan.admin.ui.home.newsfeed.publicpost.reply

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.evan.admin.R
import com.evan.admin.data.db.entities.Comments
import com.evan.admin.data.db.entities.Reply
import com.evan.admin.ui.home.HomeActivity
import com.evan.admin.ui.home.HomeViewModel
import com.evan.admin.ui.home.HomeViewModelFactory
import com.evan.admin.ui.home.newsfeed.publicpost.comments.ISucceslistener
import com.evan.admin.util.SharedPreferenceUtil
import com.evan.admin.util.hide
import com.evan.admin.util.show

import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class ReplyFragment (comments: Comments) : BottomSheetDialogFragment() , KodeinAware, IReplyListener,
    IReplyPostListener, ISucceslistener {

    private var mBehavior: BottomSheetBehavior<*>? = null
    override val kodein by kodein()
    private val factory : HomeViewModelFactory by instance()
    var comment: Comments?=comments
    var token:String?=""
    var name:String?=""
    var image:String?=""
    var progress_bar: ProgressBar?=null
    private lateinit var viewModel: HomeViewModel
    var img_close: ImageView?=null
    var replyAdapter: ReplyAdapter?=null
    var rcl_comments: RecyclerView?=null
    var tv_hour: TextView?=null
    var tv_name: TextView?=null
    var tv_content: TextView?=null
    var tv_reply: TextView?=null
    var img_auth: ImageView?=null
    var img_icon: ImageView?=null
    var img_reload: ImageView?=null
    var btn_sends: Button?=null
    var edt_input: EditText?=null
    var scroll: NestedScrollView?=null
    var replies: MutableList<Reply>?=null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog =
            super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        val view =
            View.inflate(context, R.layout.fragment_reply, null)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        viewModel.replyListener=this
        viewModel.replyPostListener=this
        viewModel.succeslistener=this
        tv_content=view.findViewById(R.id.tv_content)
        scroll=view.findViewById(R.id.scroll)
        img_reload=view.findViewById(R.id.img_reload)
        tv_reply=view.findViewById(R.id.tv_reply)
        edt_input=view.findViewById(R.id.edt_input)
        btn_sends=view.findViewById(R.id.btn_sends)
        img_close=view.findViewById(R.id.img_close)
        img_auth=view.findViewById(R.id.img_auth)
        tv_name=view.findViewById(R.id.tv_name)
        tv_hour=view.findViewById(R.id.tv_hour)
        progress_bar=view.findViewById(R.id.progress_bar)

        rcl_comments=view.findViewById(R.id.rcl_comments)
        img_icon=view.findViewById(R.id.img_icon)
        dialog.setContentView(view)

        mBehavior =
            BottomSheetBehavior.from(view.parent as View)

        img_close?.setOnClickListener {
            (activity as HomeActivity?)!!.onBottomCommentsBackPress()
            dismiss()
        }
        token = SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)
        name = SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_NAME)
        image = SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_IMAGE)
        viewModel.getReply(token!!,comment?.Id!!)

        if(comment?.Type==2){
            img_auth?.setImageDrawable(context?.getDrawable(R.drawable.correct))
        }
        else{
            img_auth?.setImageDrawable(context?.getDrawable(R.drawable.correct_blue))
        }
        tv_name?.text=comment?.UserName
        tv_content?.text=comment?.Content
        Glide.with(this)
            .load(comment?.UserImage).dontAnimate()
            .into(img_icon!!)
        try {
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val past: Date = format.parse(comment?.Created)
            val currentDate = format.format(Date())
            val now: Date = format.parse(currentDate)

            val seconds: Long =
                TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime())
            val minutes: Long =
                TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime())
            val hours: Long =
                TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime())
            val days: Long = TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime())
            if (seconds < 60) {
                Log.e("ago","ago"+seconds+" seconds ago")
                tv_hour?.text=seconds.toString()+" seconds ago"
            } else if (minutes < 60) {
                println("$minutes minutes ago")
                Log.e("ago","ago"+minutes+" minutes ago")
                tv_hour?.text=minutes.toString()+" minutes ago"
            } else if (hours < 24) {
                println("$hours hours ago")
                Log.e("ago","ago"+hours+" hours ago")
                tv_hour?.text=hours.toString()+" hours ago"
            } else {
                println("$days days ago")
                Log.e("ago","ago"+days+" days ago")
                tv_hour?.text=days.toString()+"  days ago"
            }
        } catch (j: Exception) {
            j.printStackTrace()
        }
        rcl_comments?.isNestedScrollingEnabled=false
        img_reload?.setOnClickListener {
            viewModel.getReply(token!!,comment?.Id!!)
        }
        btn_sends?.setOnClickListener {
            var content:String=""
            content=edt_input?.text.toString()
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val currentDate = sdf.format(Date())
            viewModel?.createReply(token!!,"Admin",content,currentDate,1,3,"https://fiverr-res.cloudinary.com/images/q_auto,f_auto/gigs/125568526/original/cd9c93141521436a112722e8c5c0c7ba0d60a4a2/be-your-telegram-group-admin.jpg",comment?.Id!!)

        }
        return dialog
    }
    override fun onStart() {
        super.onStart()
        mBehavior?.setState(BottomSheetBehavior.STATE_EXPANDED)
    }
    var lmChat: LinearLayoutManager?=null
    override fun load(data: MutableList<Reply>?) {
        tv_reply?.text=data?.size.toString()+" Reply"
        replies=data

        replyAdapter = ReplyAdapter(context!!,data!!)
        lmChat = LinearLayoutManager(activity)
        lmChat?.setOrientation(LinearLayoutManager.VERTICAL)
        lmChat?.setStackFromEnd(true)

        rcl_comments?.setLayoutManager(lmChat)
        rcl_comments?.adapter=replyAdapter
        //rcl_comments?.smoothScrollToPosition(10);
        //   rcl_comments?.scrollToPosition(rcl_comments?.getAdapter()?.getItemCount()!! - 1);


    }

    override fun onStarted() {
        progress_bar?.show()
    }

    override fun onSuccess(message: String) {
        Toast.makeText(context!!,message, Toast.LENGTH_SHORT).show()
        viewModel.getReply(token!!,comment?.Id!!)
        viewModel.geReplyAgain(token!!,comment?.Id!!)
        edt_input?.setText("")
    }

    override fun onFailure(message: String) {
        Toast.makeText(context!!,message, Toast.LENGTH_SHORT).show()
    }

    override fun onEnd() {
        progress_bar?.hide()
    }


    override fun onShow() {
        scroll?.fullScroll(View.FOCUS_DOWN)
        // lmChat?.scrollToPosition(15)
        // rcl_comments?.getLayoutManager()!!.smoothScrollToPosition(rcl_comments!!, null, commentsAdapter?.getItemCount()!! - 1);

    }
}