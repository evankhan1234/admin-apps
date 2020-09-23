package com.evan.admin.ui.home.notice

import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatImageButton
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.evan.admin.R
import com.evan.admin.data.db.entities.FirebaseToken
import com.evan.admin.data.db.entities.Notice
import com.evan.admin.data.network.post.Push
import com.evan.admin.data.network.post.PushPost
import com.evan.admin.ui.home.HomeActivity
import com.evan.admin.ui.home.HomeViewModel
import com.evan.admin.ui.home.HomeViewModelFactory
import com.evan.admin.ui.home.store.customer.CustomerModelFactory
import com.evan.admin.ui.home.store.customer.CustomerViewModel
import com.evan.admin.ui.home.store.customer.ICustomerSuccessListener
import com.evan.admin.util.SharedPreferenceUtil
import com.evan.admin.util.hide
import com.evan.admin.util.notice_update
import com.evan.admin.util.show
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import java.util.*


class NoticeViewFragment : Fragment(), KodeinAware, INoticeCreateListener,IFirebaseTokenListener {
    override val kodein by kodein()

    private val factory : HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel

    var img_notice: ImageView?=null
    var tv_title: EditText?=null
    var tv_content: EditText?=null
    var btn_ok: Button?=null
    var notice: Notice?=null
    var image_address: String?=""
    var img_user_add: AppCompatImageButton?=null
    var progress_bar: ProgressBar?=null
    var token:String?=""
    var tokenList: MutableList<FirebaseToken>?=null
    var pushPost: PushPost?=null
    var push: Push?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_notice_view, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        viewModel?.noticeCreateListener=this
        viewModel?.firebaseTokenListener=this
        img_notice=root?.findViewById(R.id.img_notice)
        tv_title=root?.findViewById(R.id.tv_title)
        tv_content=root?.findViewById(R.id.tv_content)
        btn_ok=root?.findViewById(R.id.btn_ok)
        img_user_add=root?.findViewById(R.id.img_user_add)
        progress_bar=root?.findViewById(R.id.progress_bar)
        token = SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)
        val args: Bundle? = arguments
        if (args != null) {
            if (args?.containsKey(Notice::class.java.getSimpleName()) != null) {
                notice = args?.getParcelable(Notice::class.java.getSimpleName())
                tv_title?.setText(notice?.Title)
                tv_content?.setText(notice?.Content)
                Glide.with(context!!)
                    .load(notice?.Image)
                    .into(img_notice!!)
                image_address=notice?.Image
            }

        }
        if (notice_update.equals("Add")){
            btn_ok?.setText("Create")
        }
        else{
            btn_ok?.setText("Update")
        }
        img_user_add?.setOnClickListener {
            (activity as HomeActivity?)!!.openImageChooser()
        }
        btn_ok?.setOnClickListener {
            var tile: String = ""
            var content: String = ""
            var product_code: String = ""
            var sell_price: String = ""
            var supplier_price: String = ""
            var stock: String = ""
            val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            val currentDate = sdf.format(Date())
            tile = tv_title?.text.toString()
            content = tv_content?.text.toString()

            if (notice_update.equals("Add")){
                viewModel?.createNewPost(token!!,tile,content,image_address!!,currentDate,1,2)
            }
            else{
                viewModel?.createUpdatePost(token!!,notice?.Id!!,tile,content,image_address!!,currentDate)
            }
        }

        viewModel?.getToken()
        return root
    }
    fun showImage(temp: String?) {
//        image_address="http://hathbazzar.com/"+temp
//        Log.e("for","Image"+temp)
//        Glide.with(this)
//            .load("http://hathbazzar.com/"+temp)
//            .into(img_background_mypage!!)
//        img_user_add?.visibility=View.INVISIBLE

        image_address = "http://192.168.0.110/" + temp
        Log.e("for", "Image" + temp)
        Glide.with(this)
            .load("http://192.168.0.110/" + temp)
            .into(img_notice!!)
        img_user_add?.visibility = View.INVISIBLE
    }

    override fun onStarted() {
        progress_bar?.show()
    }

    override fun onEnd() {
        progress_bar?.hide()
    }

    override fun onSuccess(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
        progress_bar?.show()
        for (i in tokenList!!.indices) {
            push= Push("Notice",tv_title?.text.toString())
            pushPost= PushPost(tokenList?.get(i)?.Token,push)
            viewModel.sendPush("key=AAAAdCyJ2hw:APA91bGF6x20oQnuC2ZeAXsJju-OCAZ67dBpQvaLx7h18HSAnhl9CPWupCJaV0552qJvm1qIHL_LAZoOvv5oWA9Iraar_XQkWe3JEUmJ1v7iKq09QYyPB3ZGMeSinzC-GlKwpaJU_IvO",pushPost!!)
        }

        if (activity is HomeActivity) {
            progress_bar?.hide()
            (activity as HomeActivity).onBackPressed()
        }
    }

    override fun onFailure(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun unit(shop: MutableList<FirebaseToken>?) {
        tokenList=shop
    }


}