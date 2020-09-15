package com.evan.admin.ui.home.newsfeed.ownpost

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
import com.evan.admin.data.db.entities.Post

import kotlinx.android.synthetic.main.layout_own_post_list.view.*
import kotlinx.android.synthetic.main.layout_own_post_list.view.img_auth
import kotlinx.android.synthetic.main.layout_own_post_list.view.img_icon
import kotlinx.android.synthetic.main.layout_own_post_list.view.img_image
import kotlinx.android.synthetic.main.layout_own_post_list.view.text_view
import kotlinx.android.synthetic.main.layout_own_post_list.view.tv_content
import kotlinx.android.synthetic.main.layout_own_post_list.view.tv_hour
import kotlinx.android.synthetic.main.layout_own_post_list.view.tv_name
import kotlinx.android.synthetic.main.layout_public_post_list.view.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class OwnPostAdapter (val context: Context, val ownPostUpdatedListener: IOwnPostUpdatedListener) :
    PagedListAdapter<Post, RecyclerView.ViewHolder>(NewsDiffCallback) {
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
                ownPostUpdatedListener
            )
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    companion object {
        val NewsDiffCallback = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem.Id == newItem.Id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem == newItem
            }
        }
    }

}

class AlertViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(context: Context, post: Post?, position: Int, listener: IOwnPostUpdatedListener) {

        if (post != null) {

            itemView.text_view.setOnClickListener {
                listener.onUpdate(post)
            }
            Glide.with(context)
                .load(post?.Image).dontAnimate()
                .into(itemView.img_icon!!)

            if(post.Picture.equals("empty")){
                itemView.img_image!!.visibility=View.GONE

            }
            else{
                itemView.img_image!!.visibility=View.VISIBLE
            }
            Glide.with(context)
                .load(post?.Picture).dontAnimate()
                .into(itemView.img_image!!)

            if(post?.Type==3){

                itemView?.img_auth?.setImageDrawable(context?.getDrawable(R.drawable.admin_correct))
            }
            else{

                itemView?.img_auth?.setImageDrawable(context?.getDrawable(R.drawable.correct_blue))
            }
            itemView.tv_content.text =post?.Content
            itemView.tv_name.text =post?.Name
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
                    itemView.tv_hour.text=seconds.toString()+" seconds ago"
                } else if (minutes < 60) {
                    println("$minutes minutes ago")
                    Log.e("ago","ago"+minutes+" minutes ago")
                    itemView.tv_hour.text=minutes.toString()+" minutes ago"
                } else if (hours < 24) {
                    println("$hours hours ago")
                    Log.e("ago","ago"+hours+" hours ago")
                    itemView.tv_hour.text=hours.toString()+" hours ago"
                } else {
                    println("$days days ago")
                    Log.e("ago","ago"+days+" days ago")
                    itemView.tv_hour.text=days.toString()+"  days ago"
                }
            } catch (j: Exception) {
                j.printStackTrace()
            }

        }
    }

    companion object {
        fun create(parent: ViewGroup): AlertViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_own_post_list, parent, false)

            return AlertViewHolder(view)
        }
    }


}