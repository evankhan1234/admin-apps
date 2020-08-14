package com.evan.admin.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.evan.admin.R
import com.evan.admin.data.db.entities.Post
import com.evan.admin.data.db.entities.Shop
import com.evan.admin.ui.home.dashboard.DashboardFragment
import com.evan.admin.ui.home.orders.OrdersFragment
import com.evan.admin.ui.home.store.StoreFragment
import com.evan.admin.ui.home.store.inactive_shop.InactiveShopFragment
import com.evan.admin.ui.home.store.inactive_shop.InactiveShopViewFragment
import com.evan.admin.ui.home.store.post.PostFragment
import com.evan.admin.ui.home.store.post.PostViewFragment
import com.evan.admin.util.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_layout.*
import kotlinx.android.synthetic.main.bottom_navigation_layout.*

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
    }
    fun btn_home_clicked(view: View) {
        setUpHeader(FRAG_TOP)
        afterClickTabItem(FRAG_TOP, null)
        setUpFooter(FRAG_TOP)


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
                newFrag = OrdersFragment()
            }
            FRAG_INACTIVE_POST -> {
                newFrag = PostFragment()
            }
            FRAG_INACTIVE_SHOP->{
                newFrag = InactiveShopFragment()
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

    fun goToViewInActiveShopFragment(shop: Shop) {
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
            FRAG_INACTIVE_SHOP->{
                ll_back_header?.visibility = View.VISIBLE
                rlt_header?.visibility = View.GONE
                tv_details.text = resources.getString(R.string.inactive_shop)
                btn_footer_store.setSelected(true)
            }
            FRAG_INACTIVE_POST_VIEW -> {
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

}
