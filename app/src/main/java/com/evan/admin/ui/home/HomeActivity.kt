package com.evan.admin.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.evan.admin.BuildConfig
import com.evan.admin.R
import com.evan.admin.data.db.entities.*
import com.evan.admin.ui.auth.LoginActivity
import com.evan.admin.ui.home.dashboard.DashboardFragment
import com.evan.admin.ui.home.newsfeed.NewsfeedFragment
import com.evan.admin.ui.home.newsfeed.ownpost.PostBottomsheetFragment
import com.evan.admin.ui.home.newsfeed.publicpost.comments.CommentsFragment
import com.evan.admin.ui.home.notice.NoticeViewFragment
import com.evan.admin.ui.home.order.OrderFragment
import com.evan.admin.ui.home.order.details.OrderDetailsFragment
import com.evan.admin.ui.home.store.StoreFragment
import com.evan.admin.ui.home.store.active_shop.ActiveShopFragment
import com.evan.admin.ui.home.store.customer.CustomerFragment
import com.evan.admin.ui.home.store.customer.CustomerViewFragment
import com.evan.admin.ui.home.store.inactive_shop.InactiveShopFragment
import com.evan.admin.ui.home.store.inactive_shop.InactiveShopViewFragment
import com.evan.admin.ui.home.store.post.PostFragment
import com.evan.admin.ui.home.store.post.PostViewFragment
import com.evan.admin.ui.home.store.products.CreateProductFragment
import com.evan.admin.ui.home.store.products.ProductPagerFragment
import com.evan.admin.util.*
import com.evan.dokan.ui.home.notice.NoticeFragment
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_layout.*
import kotlinx.android.synthetic.main.bottom_navigation_layout.*
import java.io.File
import java.io.IOException

class HomeActivity : AppCompatActivity() {
    var mFragManager: FragmentManager? = null
    var fragTransaction: FragmentTransaction? = null
    var mCurrentFrag: Fragment? = null
    var CURRENT_PAGE: Int? = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setUpHeader(FRAG_TOP)
        afterClickTabItem(FRAG_TOP, null)
        setUpFooter(FRAG_TOP)
        img_header_back?.setOnClickListener {
            onBackPressed()
        }
        btn_orders?.setOnClickListener {
            goToNewsfeedFragment()

        }
        btn_logout?.setOnClickListener {
            Toast.makeText(this,"Successfully Logout", Toast.LENGTH_SHORT).show()
            SharedPreferenceUtil.saveShared(this, SharedPreferenceUtil.TYPE_AUTH_TOKEN, "")
            SharedPreferenceUtil.saveShared(this, SharedPreferenceUtil.TYPE_FRESH, "")
            val intent=Intent(this, LoginActivity::class.java)
            startActivity(intent)
             finishs()

        }
    }
    fun btn_home_clicked(view: View) {
        setUpHeader(FRAG_TOP)
        afterClickTabItem(FRAG_TOP, null)
        setUpFooter(FRAG_TOP)


    }
    fun goToNewsfeedFragment(){
        setUpHeader(FRAG_NEWSFEED)
        afterClickTabItem(FRAG_NEWSFEED, null)
    }

    fun btn_store_clicked(view: View) {
        setUpHeader(FRAG_STORE)
        afterClickTabItem(FRAG_STORE, null)
        setUpFooter(FRAG_STORE)


    }

    fun btn_orders_clicked(view: View) {
        setUpHeader(FRAG_ORDER)
        afterClickTabItem(FRAG_ORDER, null)
        setUpFooter(FRAG_ORDER)
    }
    fun afterClickTabItem(fragId: Int, obj: Any?) {
        addFragment(fragId, false, obj)

    }
    fun goToInactivePostFragment() {
        setUpHeader(FRAG_INACTIVE_POST)
        addFragment(FRAG_INACTIVE_POST, true, null)

    }
    fun goToInactiveShopFragment() {
        setUpHeader(FRAG_INACTIVE_SHOP)
        addFragment(FRAG_INACTIVE_SHOP, true, null)

    }
    fun goToActiveShopFragment() {
        setUpHeader(FRAG_ACTIVE_SHOP)
        addFragment(FRAG_ACTIVE_SHOP, true, null)

    }
    fun goToProductFragment() {
        setUpHeader(FRAG_PRODUCT)
        addFragment(FRAG_PRODUCT, true, null)

    }
    fun goToCreateProductFragment() {
        setUpHeader(FRAG_CREATE_PRODUCT)
        addFragment(FRAG_CREATE_PRODUCT, true, null)

    }
    fun goToCustomerFragment() {
        setUpHeader(FRAG_CUSTOMER)
        addFragment(FRAG_CUSTOMER, true, null)

    }
    fun goToNoticeFragment() {
        setUpHeader(FRAG_NOTICE)
        addFragment(FRAG_NOTICE, true, null)

    }
    fun goToNoticeAddFragment() {
        setUpHeader(FRAG_NOTICE_ADD)
        addFragment(FRAG_NOTICE_ADD, true, null)

    }
    fun onBottomBackPress(){
        val f = getVisibleFragment()
        if(f is NewsfeedFragment){
            f.reload()
        }
    }
    fun onBottomCommentsBackPress(){
        val f = getVisibleFragmentsForComments()
        if(f is CommentsFragment){
            f.reload()
        }
    }
    fun getVisibleFragmentsForComments(): Fragment? {
        val fragmentManager = mFragManager
        val fragments = fragmentManager!!.fragments
        fragments.reverse()
        for (fragment in fragments!!) {
            if(fragment is CommentsFragment ){

                return fragment
            }
        }
        return null
    }
    fun addFragment(fragId: Int, isHasAnimation: Boolean, obj: Any?) {
        // init fragment manager
        mFragManager = supportFragmentManager
        // create transaction
        fragTransaction = mFragManager?.beginTransaction()
        //check if there is any backstack if yes then remove it
        val count = mFragManager?.getBackStackEntryCount()
        if (count != 0) {
            //this will clear the back stack and displays no animation on the screen
            // mFragManager?.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
        // check current fragment is wanted fragment
        if (mCurrentFrag != null && mCurrentFrag!!.getTag() != null && mCurrentFrag!!.getTag() == fragId.toString()) {
            return
        }
        var newFrag: Fragment? = null
        // identify which fragment will be called
        when (fragId) {
            FRAG_TOP -> {
                newFrag = DashboardFragment()
            }
            FRAG_STORE -> {
                newFrag = StoreFragment()
            }
            FRAG_ORDER -> {
                newFrag = OrderFragment()
            }
            FRAG_INACTIVE_POST -> {
                newFrag = PostFragment()
            }
            FRAG_INACTIVE_SHOP->{
                newFrag = InactiveShopFragment()
            }
            FRAG_ACTIVE_SHOP->{
                newFrag = ActiveShopFragment()
            }
            FRAG_PRODUCT->{
                newFrag = ProductPagerFragment()
            }
            FRAG_NEWSFEED->{
                newFrag = NewsfeedFragment()
            }
            FRAG_CREATE_PRODUCT->{
                newFrag = CreateProductFragment()
            }
            FRAG_CUSTOMER->{
                newFrag = CustomerFragment()
            }
            FRAG_NOTICE->{
                newFrag = NoticeFragment()
            }
            FRAG_NOTICE_ADD->{
                newFrag = NoticeViewFragment()
            }
        }
        mCurrentFrag = newFrag
        // init argument
        if (obj != null) {
            val args = Bundle()
        }
        // set animation
        if (isHasAnimation) {
            fragTransaction!!.setCustomAnimations(
                R.anim.view_transition_in_left,
                R.anim.view_transition_out_left,
                R.anim.view_transition_in_right,
                R.anim.view_transition_out_right
            )
        }
        // param 1: container id, param 2: new fragment, param 3: fragment id

        fragTransaction?.replace(R.id.main_container, newFrag!!, fragId.toString())
        // prevent showed when user press back fabReview
        fragTransaction?.addToBackStack(fragId.toString())
        //  fragTransaction?.hide(active).show(guideFragment).commit();
        fragTransaction!!.commit()

    }
    fun goToOrderDetailsFragment(order: Order) {
        setUpHeader(FRAG_ORDER_DETAILS)
        mFragManager = supportFragmentManager
        // create transaction
        var fragId:Int?=0
        fragId=FRAG_ORDER_DETAILS
        fragTransaction = mFragManager?.beginTransaction()
        //check if there is any backstack if yes then remove it
        val count = mFragManager?.getBackStackEntryCount()
        if (count != 0) {
            //this will clear the back stack and displays no animation on the screen
            // mFragManager?.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
        // check current fragment is wanted fragment
        if (mCurrentFrag != null && mCurrentFrag!!.getTag() != null && mCurrentFrag!!.getTag() == fragId.toString()) {
            return
        }
        var newFrag: Fragment? = null

        // identify which fragment will be called

        newFrag = OrderDetailsFragment()
        val b= Bundle()
        b.putParcelable(Order::class.java?.getSimpleName(), order)
//        var list: ArrayList<Product> = arrayListOf()
//        b.putParcelableArrayList(Product::class.java?.getSimpleName(), list)

        newFrag.setArguments(b)

        mCurrentFrag = newFrag

        fragTransaction!!.setCustomAnimations(
            R.anim.view_transition_in_left,
            R.anim.view_transition_out_left,
            R.anim.view_transition_in_right,
            R.anim.view_transition_out_right
        )

        // param 1: container id, param 2: new fragment, param 3: fragment id

        fragTransaction?.add(R.id.main_container, newFrag!!, fragId.toString())
        // prevent showed when user press back fabReview
        fragTransaction?.addToBackStack(fragId.toString())
        //  fragTransaction?.hide(active).show(guideFragment).commit();
        fragTransaction!!.commit()

    }
    fun goToNoticeDetailsFragment(notice: Notice) {
        setUpHeader(FRAG_NOTICE_DETAILS)
        mFragManager = supportFragmentManager
        var fragId:Int?=0
        fragId= FRAG_NOTICE_DETAILS
        fragTransaction = mFragManager?.beginTransaction()
        val count = mFragManager?.getBackStackEntryCount()
        if (count != 0) {

        }
        if (mCurrentFrag != null && mCurrentFrag!!.getTag() != null && mCurrentFrag!!.getTag() == fragId.toString()) {
            return
        }
        var newFrag: Fragment? = null
        newFrag = NoticeViewFragment()
        val b= Bundle()
        b.putParcelable(Notice::class.java?.getSimpleName(), notice)
        newFrag.setArguments(b)
        mCurrentFrag = newFrag
        fragTransaction!!.setCustomAnimations(
            R.anim.view_transition_in_left,
            R.anim.view_transition_out_left,
            R.anim.view_transition_in_right,
            R.anim.view_transition_out_right
        )

        fragTransaction?.replace(R.id.main_container, newFrag!!, fragId.toString())
        fragTransaction?.addToBackStack(fragId.toString())
        fragTransaction!!.commit()

    }
    fun goToUpdateProductFragment(product: Product) {
        setUpHeader(FRAG_UPDATE_PRODUCT)
        mFragManager = supportFragmentManager
        // create transaction
        var fragId:Int?=0
        fragId=FRAG_UPDATE_PRODUCT
        fragTransaction = mFragManager?.beginTransaction()
        //check if there is any backstack if yes then remove it
        val count = mFragManager?.getBackStackEntryCount()
        if (count != 0) {
            //this will clear the back stack and displays no animation on the screen
            // mFragManager?.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
        // check current fragment is wanted fragment
        if (mCurrentFrag != null && mCurrentFrag!!.getTag() != null && mCurrentFrag!!.getTag() == fragId.toString()) {
            return
        }
        var newFrag: Fragment? = null

        // identify which fragment will be called

        newFrag = CreateProductFragment()
        val b= Bundle()
        b.putParcelable(Product::class.java?.getSimpleName(), product)

        newFrag.setArguments(b)

        mCurrentFrag = newFrag

        fragTransaction!!.setCustomAnimations(
            R.anim.view_transition_in_left,
            R.anim.view_transition_out_left,
            R.anim.view_transition_in_right,
            R.anim.view_transition_out_right
        )

        // param 1: container id, param 2: new fragment, param 3: fragment id

        fragTransaction?.replace(R.id.main_container, newFrag!!, fragId.toString())
        // prevent showed when user press back fabReview
        fragTransaction?.addToBackStack(fragId.toString())
        //  fragTransaction?.hide(active).show(guideFragment).commit();
        fragTransaction!!.commit()

    }
    fun goToViewInActiveFragment(post: Post) {
        setUpHeader(FRAG_INACTIVE_POST_VIEW)
        mFragManager = supportFragmentManager
        // create transaction
        var fragId:Int?=0
        fragId=FRAG_INACTIVE_POST_VIEW
        fragTransaction = mFragManager?.beginTransaction()
        //check if there is any backstack if yes then remove it
        val count = mFragManager?.getBackStackEntryCount()
        if (count != 0) {
            //this will clear the back stack and displays no animation on the screen
            // mFragManager?.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
        // check current fragment is wanted fragment
        if (mCurrentFrag != null && mCurrentFrag!!.getTag() != null && mCurrentFrag!!.getTag() == fragId.toString()) {
            return
        }
        var newFrag: Fragment? = null

        // identify which fragment will be called

        newFrag = PostViewFragment()
        val b= Bundle()
        b.putParcelable(Post::class.java?.getSimpleName(), post)

        newFrag.setArguments(b)

        mCurrentFrag = newFrag

        fragTransaction!!.setCustomAnimations(
            R.anim.view_transition_in_left,
            R.anim.view_transition_out_left,
            R.anim.view_transition_in_right,
            R.anim.view_transition_out_right
        )

        // param 1: container id, param 2: new fragment, param 3: fragment id

        fragTransaction?.replace(R.id.main_container, newFrag!!, fragId.toString())
        // prevent showed when user press back fabReview
        fragTransaction?.addToBackStack(fragId.toString())
        //  fragTransaction?.hide(active).show(guideFragment).commit();
        fragTransaction!!.commit()

    }
    fun goToViewCustomerFragment(customer: Customer) {
        setUpHeader(FRAG_VIEW_CUSTOMER)
        mFragManager = supportFragmentManager
        // create transaction
        var fragId:Int?=0
        fragId=FRAG_VIEW_CUSTOMER
        fragTransaction = mFragManager?.beginTransaction()
        //check if there is any backstack if yes then remove it
        val count = mFragManager?.getBackStackEntryCount()
        if (count != 0) {
            //this will clear the back stack and displays no animation on the screen
            // mFragManager?.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
        // check current fragment is wanted fragment
        if (mCurrentFrag != null && mCurrentFrag!!.getTag() != null && mCurrentFrag!!.getTag() == fragId.toString()) {
            return
        }
        var newFrag: Fragment? = null

        // identify which fragment will be called

        newFrag = CustomerViewFragment()
        val b= Bundle()
        b.putParcelable(Customer::class.java?.getSimpleName(), customer)

        newFrag.setArguments(b)

        mCurrentFrag = newFrag

        fragTransaction!!.setCustomAnimations(
            R.anim.view_transition_in_left,
            R.anim.view_transition_out_left,
            R.anim.view_transition_in_right,
            R.anim.view_transition_out_right
        )

        // param 1: container id, param 2: new fragment, param 3: fragment id

        fragTransaction?.replace(R.id.main_container, newFrag!!, fragId.toString())
        // prevent showed when user press back fabReview
        fragTransaction?.addToBackStack(fragId.toString())
        //  fragTransaction?.hide(active).show(guideFragment).commit();
        fragTransaction!!.commit()

    }
    fun goToViewInActiveShopFragment(shop: Shop,type: String) {
        setUpHeader(FRAG_INACTIVE_SHOP_VIEW)
        mFragManager = supportFragmentManager
        // create transaction
        var fragId:Int?=0
        fragId=FRAG_INACTIVE_SHOP_VIEW
        fragTransaction = mFragManager?.beginTransaction()
        //check if there is any backstack if yes then remove it
        val count = mFragManager?.getBackStackEntryCount()
        if (count != 0) {
            //this will clear the back stack and displays no animation on the screen
            // mFragManager?.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
        // check current fragment is wanted fragment
        if (mCurrentFrag != null && mCurrentFrag!!.getTag() != null && mCurrentFrag!!.getTag() == fragId.toString()) {
            return
        }
        var newFrag: Fragment? = null

        // identify which fragment will be called

        newFrag = InactiveShopViewFragment()
        val b= Bundle()
        b.putParcelable(Shop::class.java?.getSimpleName(), shop)
        b.putString("type",type)
        newFrag.setArguments(b)

        mCurrentFrag = newFrag

        fragTransaction!!.setCustomAnimations(
            R.anim.view_transition_in_left,
            R.anim.view_transition_out_left,
            R.anim.view_transition_in_right,
            R.anim.view_transition_out_right
        )

        // param 1: container id, param 2: new fragment, param 3: fragment id

        fragTransaction?.replace(R.id.main_container, newFrag!!, fragId.toString())
        // prevent showed when user press back fabReview
        fragTransaction?.addToBackStack(fragId.toString())
        //  fragTransaction?.hide(active).show(guideFragment).commit();
        fragTransaction!!.commit()

    }
    override fun onBackPressed() {
        super.onBackPressed()

        val f = getVisibleFragment()
        if (f != null)
        {
            if (f is StoreFragment) {
                val storeFragment: StoreFragment =
                    mFragManager?.findFragmentByTag(FRAG_STORE.toString()) as StoreFragment
                setUpHeader(FRAG_STORE)
            }

          else if (f is DashboardFragment) {
                val storeFragment: DashboardFragment =
                    mFragManager?.findFragmentByTag(FRAG_TOP.toString()) as DashboardFragment
                //  storeFragment.removeChild()
                setUpHeader(FRAG_TOP)
            }
            else  if (f is PostFragment) {
                val storeFragment: PostFragment =
                    mFragManager?.findFragmentByTag(FRAG_INACTIVE_POST.toString()) as PostFragment
                //  storeFragment.removeChild()
                setUpHeader(FRAG_INACTIVE_POST)
            }
            else   if (f is InactiveShopFragment) {
                val storeFragment: InactiveShopFragment =
                    mFragManager?.findFragmentByTag(FRAG_INACTIVE_SHOP.toString()) as InactiveShopFragment
                //  storeFragment.removeChild()
                setUpHeader(FRAG_INACTIVE_SHOP)
            }
            else   if (f is ProductPagerFragment) {
                setUpHeader(FRAG_PRODUCT)
            }
            else   if (f is CustomerFragment) {
                setUpHeader(FRAG_CUSTOMER)
            }
            else   if (f is NoticeFragment) {
                setUpHeader(FRAG_NOTICE)
            }
            else   if (f is OrderFragment) {
                setUpHeader(FRAG_ORDER)
            }
        }

    }
    fun setUpHeader(type: Int) {
        when (type) {
            FRAG_TOP -> {
                ll_back_header?.visibility = View.GONE
                rlt_header?.visibility = View.VISIBLE
                tv_title.text = resources.getString(R.string.home)
            }
            FRAG_STORE -> {
                ll_back_header?.visibility = View.GONE
                rlt_header?.visibility = View.VISIBLE
                tv_title.text = resources.getString(R.string.store)

            }
            FRAG_ORDER -> {
                ll_back_header?.visibility = View.GONE
                rlt_header?.visibility = View.VISIBLE
                tv_title.text = resources.getString(R.string.order)

            }
            FRAG_INACTIVE_POST -> {
                ll_back_header?.visibility = View.VISIBLE
                rlt_header?.visibility = View.GONE
                tv_details.text = resources.getString(R.string.inactive_post)
                btn_footer_store.setSelected(true)

            }
            FRAG_ORDER_DETAILS -> {
                ll_back_header?.visibility = View.VISIBLE
                rlt_header?.visibility = View.GONE
                tv_details.text = resources.getString(R.string.details)
                btn_footer_orders.setSelected(true)

            }
            FRAG_NOTICE-> {
                ll_back_header?.visibility = View.VISIBLE
                rlt_header?.visibility = View.GONE
                tv_details.text = resources.getString(R.string.notice)
                btn_footer_store.setSelected(true)

            }
            FRAG_NOTICE_ADD-> {
                ll_back_header?.visibility = View.VISIBLE
                rlt_header?.visibility = View.GONE
                tv_details.text = resources.getString(R.string.create_notice)
                btn_footer_store.setSelected(true)

            }
            FRAG_NOTICE_DETAILS-> {
                ll_back_header?.visibility = View.VISIBLE
                rlt_header?.visibility = View.GONE
                tv_details.text = resources.getString(R.string.update_notice)
                btn_footer_store.setSelected(true)

            }
            FRAG_CUSTOMER -> {
                ll_back_header?.visibility = View.VISIBLE
                rlt_header?.visibility = View.GONE
                tv_details.text = resources.getString(R.string.customer)
                btn_footer_store.setSelected(true)

            }
            FRAG_NEWSFEED->{
                ll_back_header?.visibility = View.VISIBLE
                rlt_header?.visibility = View.GONE
                tv_details.text = resources.getString(R.string.newsfeed)
            }
            FRAG_PRODUCT -> {
                ll_back_header?.visibility = View.VISIBLE
                rlt_header?.visibility = View.GONE
                tv_details.text = resources.getString(R.string.product)
                btn_footer_store.setSelected(true)

            }
            FRAG_CREATE_PRODUCT -> {
                ll_back_header?.visibility = View.VISIBLE
                rlt_header?.visibility = View.GONE
                tv_details.text = resources.getString(R.string.create_product)
                btn_footer_store.setSelected(true)

            }
            FRAG_UPDATE_PRODUCT -> {
                ll_back_header?.visibility = View.VISIBLE
                rlt_header?.visibility = View.GONE
                tv_details.text = resources.getString(R.string.update_product)
                btn_footer_store.setSelected(true)

            }
            FRAG_INACTIVE_SHOP->{
                ll_back_header?.visibility = View.VISIBLE
                rlt_header?.visibility = View.GONE
                tv_details.text = resources.getString(R.string.inactive_shop)
                btn_footer_store.setSelected(true)
            }
            FRAG_ACTIVE_SHOP->{
                ll_back_header?.visibility = View.VISIBLE
                rlt_header?.visibility = View.GONE
                tv_details.text = resources.getString(R.string.active_shop)
                btn_footer_store.setSelected(true)
            }
            FRAG_INACTIVE_POST_VIEW -> {
                ll_back_header?.visibility = View.VISIBLE
                rlt_header?.visibility = View.GONE
                tv_details.text = resources.getString(R.string.details)
                btn_footer_store.setSelected(true)

            }
            FRAG_VIEW_CUSTOMER -> {
                ll_back_header?.visibility = View.VISIBLE
                rlt_header?.visibility = View.GONE
                tv_details.text = resources.getString(R.string.details)
                btn_footer_store.setSelected(true)

            }

            FRAG_INACTIVE_SHOP_VIEW -> {
                ll_back_header?.visibility = View.VISIBLE
                rlt_header?.visibility = View.GONE
                tv_details.text = resources.getString(R.string.details)
                btn_footer_store.setSelected(true)

            }
        }
    }
    fun setUpFooter(type: Int) {
        setUnselectAllmenu()
        CURRENT_PAGE = type

        when (type) {
            FRAG_TOP -> {
                shadow_line?.visibility = View.VISIBLE
                bottom_navigation?.visibility = View.VISIBLE

                btn_footer_home.setSelected(true)
                tv_home_menu.setSelected(true)
            }
            FRAG_STORE -> {
                shadow_line?.visibility = View.VISIBLE
                bottom_navigation?.visibility = View.VISIBLE

                btn_footer_store.setSelected(true)
                tv_store_menu.setSelected(true)
            }
            FRAG_ORDER -> {
                shadow_line?.visibility = View.VISIBLE
                bottom_navigation?.visibility = View.VISIBLE

                btn_footer_orders.setSelected(true)
                tv_orders_menu.setSelected(true)
            }



            else -> {

            }

        }
    }
    private fun setUnselectAllmenu() {
        btn_footer_home.setSelected(false)
        tv_home_menu.setSelected(false)

        btn_footer_store.setSelected(false)
        tv_store_menu.setSelected(false)

        btn_footer_orders.setSelected(false)
        tv_orders_menu.setSelected(false)

    }
    fun getVisibleFragment(): Fragment? {
        val fragmentManager = mFragManager
        val fragments = fragmentManager!!.fragments
        fragments.reverse()
        for (fragment in fragments!!) {
            if (fragment != null && fragment.isVisible) {
                return fragment
            }
        }
        return null
    }
    fun finishs(){
        finishAffinity()
    }
    private val CAMERA_PERMISSION_REQUEST_CODE = 1001
    private val RESULT_TAKE_PHOTO = 10
    private val RESULT_LOAD_IMG = 101
    private val REQUEST_EXTERNAL_STORAGE_FROM_CAPTURE = 1002
    private val RESULT_UPDATE_IMAGE = 11

    fun openImageChooser() {
        showImagePickerDialog(this, object :
            DialogActionListener {
            override fun onPositiveClick() {
                openCamera()
            }

            override fun onNegativeClick() {
                checkGalleryPermission()
            }
        })
    }
    private fun checkGalleryPermission() {
        if (isCameraePermissionGranted(this)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(
                        arrayOf<String?>(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        REQUEST_EXTERNAL_STORAGE_FROM_CAPTURE
                    )
                } else {
                    //start your camera

                    getImageFromGallery()
                }
            } else {
                getImageFromGallery()
            }
        } else {
            //required permission

            ActivityCompat.requestPermissions(
                this,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE_FROM_CAPTURE
            )
        }
    }
    fun openCamera() {
        if (isCameraePermissionGranted(this)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) !== PackageManager.PERMISSION_GRANTED&&checkSelfPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(
                        arrayOf<String?>(
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE),
                        CAMERA_PERMISSION_REQUEST_CODE
                    )
                } else {
                    //start your camera

                    takePhoto()
                }
            } else {
                takePhoto()
            }
        } else {
            //required permission

            ActivityCompat.requestPermissions(
                this,
                PERMISSIONS_CAMERA,
                CAMERA_PERMISSION_REQUEST_CODE
            )
        }
    }

    // Receive the permissions request result
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Start your camera handling here

                    takePhoto()
                }
            }
            REQUEST_EXTERNAL_STORAGE_FROM_CAPTURE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Start your camera handling here
                    getImageFromGallery()
                }
            }
        }
    }

    private var mTakeUri: Uri? = null
    private var mFile: File? = null
    private var mCurrentPhotoPath: String? = null
    private fun takePhoto() {
        val intent =
            Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(this.getPackageManager()) != null) {
            // Create the File where the photo should go
            mFile = null
            try {
                mFile = createImageFile(this)
                mCurrentPhotoPath = mFile?.getAbsolutePath()
            } catch (ex: IOException) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created


            if (mFile != null) {
                mTakeUri = FileProvider.getUriForFile(
                    this,
                    BuildConfig.APPLICATION_ID, mFile!!
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                } else {
                    val packageManager: PackageManager =
                        this.getPackageManager()
                    val activities: List<ResolveInfo> =
                        packageManager.queryIntentActivities(
                            intent,
                            PackageManager.MATCH_DEFAULT_ONLY
                        )
                    for (resolvedIntentInfo in activities) {
                        val packageName: String? =
                            resolvedIntentInfo.activityInfo.packageName
                        this.grantUriPermission(
                            packageName,
                            mTakeUri,
                            Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                        )
                    }
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mTakeUri)
                startActivityForResult(intent, RESULT_TAKE_PHOTO)
            }
        }
    }
    fun getVisibleFragments(): Fragment? {
        val fragmentManager = mFragManager
        val fragments = fragmentManager!!.fragments
        fragments.reverse()
        for (fragment in fragments!!) {
            if(fragment is PostBottomsheetFragment ){

                return fragment
            }
        }
        return null
    }
    private fun getImageFromGallery() {
        val photoPickerIntent =
            Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG)
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Check which request we're responding to
        Log.e("requestCode", resultCode.toString() + " requestCode" + requestCode)
        Log.e("RESULT_OK", "RESULT_OK" + RESULT_OK)


        when (requestCode) {
            RESULT_LOAD_IMG -> {
                try {
                    val imageUri = data?.data
                    if (imageUri != null) {
                        val file = File(getRealPathFromURI(imageUri, this))
                        goImagePreviewPage(imageUri, file)
                    }
                } catch (e: Exception) {
                    Log.e("exc", "" + e.message)
                    Toast.makeText(this,"Can not found this image", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }
            RESULT_TAKE_PHOTO -> {
                //return if photopath is null
                if(mCurrentPhotoPath == null)
                    return
                mCurrentPhotoPath = getRightAngleImage(mCurrentPhotoPath!!)
                try {
                    val imgFile = File(mCurrentPhotoPath)
                    if (imgFile.exists()) {
                        goImagePreviewPage(mTakeUri, imgFile)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            RESULT_UPDATE_IMAGE -> {
                if (data != null && data?.hasExtra("updated_image_url")) {
                    val updated_image_url: String? = data?.getStringExtra("updated_image_url")
                    Log.e("updated_image_url", "--$updated_image_url")
                    if (updated_image_url != null) {
                        if (updated_image_url == "") {

                        } else {
                            val f = getVisibleFragment()
                            if (f != null) {
                                if ( image_update.equals("profile")){
                                    val f = getVisibleFragments()
                                    if (f != null) {
                                        if (f is PostBottomsheetFragment) {

                                            f.showImage(updated_image_url)
                                        }

                                    }
                                }
                                else{
                                    if (f is CreateProductFragment) {

                                        f.showImage(updated_image_url)
                                    }
                                    else if (f is NoticeViewFragment) {

                                        f.showImage(updated_image_url)
                                    }
                                }


                            }




                        }
                    }
                }
            }





        }

    }
    fun goImagePreviewPage(uri: Uri?, imageFile: File) {
        val fileSize = imageFile.length().toInt()
        if (fileSize <= SERVER_SEND_FILE_SIZE_MAX_LIMIT) {
            val i = Intent(
                this,
                ImageUpdateActivity::class.java
            )
            i.putExtra("from", FRAG_CREATE_NEW_DELIVERY)
            temporary_profile_picture = imageFile
            temporary_profile_picture_uri = uri
            startActivityForResult(i, RESULT_UPDATE_IMAGE)
            overridePendingTransition(R.anim.right_to_left, R.anim.stand_by)
        } else {
            showDialogSuccessMessage(
                this,
                resources.getString(R.string.image_size_is_too_large),
                resources.getString(R.string.txt_close),


                null
            )
        }
    }

}
