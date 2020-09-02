package com.evan.admin.ui.home.store.products

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.evan.admin.R
import com.evan.admin.data.db.entities.ShopType
import com.google.android.material.tabs.TabLayout
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class ProductPagerFragment : Fragment() , KodeinAware, IShopTypeListener{

    override val kodein by kodein()
    private val factory: ProductModelFactory by instance()
    private var viewModel: ProductViewModel? = null

    private var mContext: Context? = null
    private var viewPager: ViewPager? = null
    private var tabs: TabLayout? = null
    var root:View?=null
    var main_container: RelativeLayout?=null
    var fragmentAdapter: ProductPagerAdapter?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_product_pager, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(ProductViewModel::class.java)

        mContext = activity
        viewPager = root?.findViewById(R.id.viewPager)
        main_container = root?.findViewById(R.id.main_container)
        tabs = root?.findViewById(R.id.tabs)


        viewModel?.shopTypeListener = this
        viewModel?.getShopType()

        return root
    }

    override fun shopType(shop: MutableList<ShopType>?) {
        if(shop?.size!! > 0){
            setTabView(shop)
        }
    }

    fun setTabView(shop: MutableList<ShopType>) {
        fragmentAdapter=
            ProductPagerAdapter(
                childFragmentManager,
                mContext!!,shop
            )
        viewPager?.apply {
            this.adapter = fragmentAdapter
            this.offscreenPageLimit = 0
            tabs?.setupWithViewPager(viewPager)
        }

        viewPager?.addOnPageChangeListener(object :
            ViewPager.OnPageChangeListener {
            override fun onPageScrolled(i: Int, positionOffset: Float, positionOffsetPx: Int) {
            }
            override fun onPageSelected(i: Int) {
                //refreshData(i)
            }
            override fun onPageScrollStateChanged(i: Int) {}
        })
        // viewPager?.setCurrentItem(1)
    }
}