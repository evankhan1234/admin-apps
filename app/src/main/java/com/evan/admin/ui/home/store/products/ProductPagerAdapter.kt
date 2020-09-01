package com.evan.admin.ui.home.store.products

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.evan.admin.data.db.entities.ShopType

class ProductPagerAdapter (
    fm: FragmentManager,
    val context: Context,
    shop: MutableList<ShopType>
) : FragmentPagerAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var categoryList = shop

    override fun getItem(position: Int): Fragment {
        return return ProductsFragment.newInstance(categoryList?.get(position)?.Id!!)
    }

    override fun getCount(): Int {
        categoryList?.let {
            return it.size
        }
        return 0
    }

    override fun getPageTitle(position: Int): String? {
        return categoryList?.get(position)?.Name
    }
}