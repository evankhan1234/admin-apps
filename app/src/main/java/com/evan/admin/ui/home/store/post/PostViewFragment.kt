package com.evan.admin.ui.home.store.post

import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.evan.admin.R
import com.evan.admin.data.db.entities.Post
import com.evan.admin.ui.home.HomeActivity
import com.evan.admin.util.SharedPreferenceUtil
import com.google.gson.Gson
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class PostViewFragment : Fragment() , KodeinAware,IPostSuccessListener{
    override val kodein by kodein()
    private val factory : PostModelFactory by instance()
    private lateinit var viewModel: PostViewModel
    var tv_hour: TextView?=null
    var tv_name: TextView?=null
    var tv_content: TextView?=null
    var progress_bar: ProgressBar?=null
    var img_icon: ImageView?=null
    var img_auth: ImageView?=null
    var img_image: ImageView?=null
    var btn_done: Button?=null
    var radio_delete: RadioButton?=null
    var radio_approve: RadioButton?=null
    var token: String? = ""
    var post:Post?=null

    var type:Int?=1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_post_view, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(PostViewModel::class.java)
        viewModel.postSuccessListener=this
        tv_hour=root.findViewById(R.id.tv_hour)
        tv_name=root.findViewById(R.id.tv_name)
        tv_content=root.findViewById(R.id.tv_content)
        progress_bar=root.findViewById(R.id.progress_bar)
        img_icon=root.findViewById(R.id.img_icon)
        img_auth=root.findViewById(R.id.img_auth)
        img_image=root.findViewById(R.id.img_image)
        btn_done=root.findViewById(R.id.btn_done)
        radio_delete=root.findViewById(R.id.radio_delete)
        radio_approve=root.findViewById(R.id.radio_approve)
        token = SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)
        val args: Bundle? = arguments
        if (args != null) {
            if (args?.containsKey(Post::class.java.getSimpleName()) != null) {
                post = args?.getParcelable(Post::class.java.getSimpleName())

                Log.e("data", "data" + Gson().toJson(post!!))

            }
        }
        init()
        setData()
        return root
    }

    fun init(){
        radio_approve?.setOnClickListener{
          type=1
        }
        radio_delete?.setOnClickListener{
            type=2
        }
        btn_done?.setOnClickListener {
            if (type==1){
                viewModel?.updatePost(token!!,post?.Id!!)
            }
            else{
                viewModel?.deletePost(token!!,post?.Id!!)
            }
            Log.e("type", "type" + type)
        }
    }
    fun setData(){
        if(!post?.Picture.equals("empty")){
            Glide.with(this)
                .load(post?.Picture).dontAnimate()
                .into(img_image!!)
        }
        else{
            img_image?.visibility=View.GONE
        }
        if(post?.Type==2){
            img_auth?.setImageDrawable(context?.getDrawable(R.drawable.correct))
        }
        else{
            img_auth?.setImageDrawable(context?.getDrawable(R.drawable.correct_blue))
        }
        tv_name?.text=post?.Name
        tv_content?.text=post?.Content
        Glide.with(this)
            .load(post?.Image).dontAnimate()
            .into(img_icon!!)
        try {
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val past: Date = format.parse(post?.Created)
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
    }

    override fun onStarted() {
        progress_bar?.visibility=View.VISIBLE
    }

    override fun onEnd() {
        progress_bar?.visibility=View.GONE
    }

    override fun onSuccess(message: String) {
        Toast.makeText(activity!!,message,Toast.LENGTH_SHORT).show()
        if ( activity is HomeActivity){
            (activity as HomeActivity).onBackPressed()
        }
    }

    override fun onFailure(message: String) {
        Toast.makeText(activity!!,message,Toast.LENGTH_SHORT).show()
    }

}