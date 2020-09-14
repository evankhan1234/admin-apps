package com.evan.admin.ui.home.store.products

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.evan.admin.R
import com.evan.admin.data.db.entities.Product
import com.evan.admin.data.db.entities.ShopType
import com.evan.admin.data.db.entities.Unit
import com.evan.admin.ui.home.HomeActivity
import com.evan.admin.ui.home.HomeViewModel
import com.evan.admin.ui.home.HomeViewModelFactory
import com.evan.admin.util.*
import com.google.gson.Gson
import com.makeramen.roundedimageview.RoundedImageView
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import java.util.*


class CreateProductFragment : Fragment(), KodeinAware, IUnitListener, ShopTypeInterface,
    ICreateProductListener {

    override val kodein by kodein()
    private val factory: HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    var unitDataAdapter: ArrayAdapter<String>? = null
    var unit: MutableList<Unit>? = null

    var shopTypeDataAdapter: ArrayAdapter<String>? = null
    var shopType: MutableList<ShopType>? = null
    var spinner_unit: Spinner? = null
    var spinner_shop_type: Spinner? = null
    var progress_bar: ProgressBar? = null
    var id_unit: Int? = null
    var root_layout: RelativeLayout? = null
    var et_product_name: EditText? = null
    var et_details: EditText? = null
    var et_product_code: EditText? = null
    var et_sell_price: EditText? = null
    var et_supplier_price: EditText? = null
    var et_stock: EditText? = null
    var btn_ok: Button? = null
    var token: String? = ""
    var product: Product? = null
    var switch_status: SwitchCompat? = null
    var id: Int? = 0
    var shopTypeId: Int? = 0
    var status: Int? = null
    var image_address: String? = ""
    var img_background_mypage: RoundedImageView? = null
    var img_user_add: AppCompatImageButton? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_create_product, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        viewModel.unitListener = this
        viewModel.createProductListener = this
        viewModel.shopTypeListener = this
        spinner_shop_type = root?.findViewById(R.id.spinner_shop_type)
        spinner_unit = root?.findViewById(R.id.spinner_unit)
        progress_bar = root?.findViewById(R.id.progress_bar)
        root_layout = root?.findViewById(R.id.root_layout)
        et_product_name = root?.findViewById(R.id.et_product_name)
        et_details = root?.findViewById(R.id.et_details)
        et_product_code = root?.findViewById(R.id.et_product_code)
        et_sell_price = root?.findViewById(R.id.et_sell_price)
        et_supplier_price = root?.findViewById(R.id.et_supplier_price)
        et_stock = root?.findViewById(R.id.et_stock)
        btn_ok = root?.findViewById(R.id.btn_ok)
        switch_status = root?.findViewById(R.id.switch_status)
        img_background_mypage = root?.findViewById(R.id.img_image)

        token = SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)
        viewModel.getUnit()
        viewModel.getShopType()
        img_user_add = root?.findViewById(R.id.img_user_add)
        img_user_add?.setOnClickListener {
            (activity as HomeActivity?)!!.openImageChooser()
        }
        val args: Bundle? = arguments
        if (args != null) {
            if (args?.containsKey(Product::class.java.getSimpleName()) != null) {
                product = args?.getParcelable(Product::class.java.getSimpleName())
                id = product?.Id
                et_product_name?.setText(product?.ItemName)
                et_details?.setText(product?.ItemDescription)
                et_product_code?.setText(product?.ItemCode)
                et_stock?.setText(product?.Stock!!.toString())
                et_sell_price?.setText(product?.SalesPrice!!.toString())
                et_supplier_price?.setText(product?.PurchasePrice!!.toString())
                switch_status?.isChecked = product?.Status == 1
                image_address = product?.Picture
                Glide.with(this)
                    .load(product?.Picture)
                    .into(img_background_mypage!!)

                Log.e("data", "data" + Gson().toJson(product))
                Handler(Looper.getMainLooper()).postDelayed({
                    /* Create an Intent that will start the Menu-Activity. */
                    for (i in unit!!.indices) {
                        if (unit!!.get(i).Id!!.equals(product?.UnitId)) {
                            spinner_unit?.setSelection(i)
                        }
                    }
                    for (i in shopType!!.indices) {
                        if (shopType!!.get(i).Id!!.equals(product?.ShopType)) {
                            spinner_shop_type?.setSelection(i)
                        }
                    }
                }, 500)
            }
        }


        btn_ok?.setOnClickListener {

            if (id == 0) {
                val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                val currentDate = sdf.format(Date())
                if (switch_status?.isChecked!!) {
                    status = 1
                } else {
                    status = 0
                }

                var product_name: String = ""
                var product_details: String = ""
                var product_code: String = ""
                var sell_price: String = ""
                var supplier_price: String = ""
                var stock: String = ""

                product_name = et_product_name?.text.toString()
                product_details = et_details?.text.toString()
                product_code = et_product_code?.text.toString()
                sell_price = et_sell_price?.text.toString()
                stock = et_stock?.text.toString()
                supplier_price = et_supplier_price?.text.toString()

                if (product_name.isNullOrEmpty() && product_details.isNullOrEmpty() && product_code.isNullOrEmpty() && sell_price.isNullOrEmpty() && supplier_price.isNullOrEmpty() && stock.isNullOrEmpty()) {
                    root_layout?.snackbar("All Field is Empty")
                } else if (product_name.isNullOrEmpty()) {
                    root_layout?.snackbar("Product Name is Empty")
                } else if (product_details.isNullOrEmpty()) {
                    root_layout?.snackbar("Product Details is Empty")
                } else if (product_code.isNullOrEmpty()) {
                    root_layout?.snackbar("Product Code is Empty")
                } else if (sell_price.isNullOrEmpty()) {
                    root_layout?.snackbar("Sell Price is Empty")
                } else if (supplier_price.isNullOrEmpty()) {
                    root_layout?.snackbar("Supplier Price is Empty")
                } else if (stock.isNullOrEmpty()) {
                    root_layout?.snackbar("Stock is Empty")
                } else if (image_address.isNullOrEmpty()) {
                    root_layout?.snackbar("Image is Empty")
                } else {
                    Log.e("Evan", "Evan")

                    viewModel.postProduct(
                        SharedPreferenceUtil.getShared(
                            activity!!,
                            SharedPreferenceUtil.TYPE_AUTH_TOKEN
                        )!!,
                        product_name!!,
                        product_details!!,
                        product_code!!,
                        image_address!!,
                        id_unit!!,
                        sell_price!!.toDouble(),
                        supplier_price!!.toDouble(),
                        shopTypeId!!,
                        stock!!.toInt(),
                        stock!!.toInt(),
                        0.0,
                        currentDate!!,
                        status!!
                    )

//                    viewModel.postSupplier(
//                        SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)!!,name,mobile,email,address,details,image_address!!,status!!,
//                        SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_SHOP_ID)!!.toInt(),currentDate)
                }
            } else {
                val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                val currentDate = sdf.format(Date())
                if (switch_status?.isChecked!!) {
                    status = 1
                } else {
                    status = 0
                }

                var product_name: String = ""
                var product_details: String = ""
                var product_code: String = ""
                var sell_price: String = ""
                var supplier_price: String = ""
                var stock: String = ""

                product_name = et_product_name?.text.toString()
                product_details = et_details?.text.toString()
                product_code = et_product_code?.text.toString()
                sell_price = et_sell_price?.text.toString()
                stock = et_stock?.text.toString()
                supplier_price = et_supplier_price?.text.toString()

                if (product_name.isNullOrEmpty() && product_details.isNullOrEmpty() && product_code.isNullOrEmpty() && sell_price.isNullOrEmpty() && supplier_price.isNullOrEmpty() && stock.isNullOrEmpty()) {
                    root_layout?.snackbar("All Field is Empty")
                } else if (product_name.isNullOrEmpty()) {
                    root_layout?.snackbar("Product Name is Empty")
                } else if (product_details.isNullOrEmpty()) {
                    root_layout?.snackbar("Product Details is Empty")
                } else if (product_code.isNullOrEmpty()) {
                    root_layout?.snackbar("Product Code is Empty")
                } else if (sell_price.isNullOrEmpty()) {
                    root_layout?.snackbar("Sell Price is Empty")
                } else if (supplier_price.isNullOrEmpty()) {
                    root_layout?.snackbar("Supplier Price is Empty")
                } else if (stock.isNullOrEmpty()) {
                    root_layout?.snackbar("Stock is Empty")
                } else if (image_address.isNullOrEmpty()) {
                    root_layout?.snackbar("Image is Empty")
                } else {
                    Log.e("Evan", "Evan")

                    viewModel.postUpdateProduct(
                        SharedPreferenceUtil.getShared(
                            activity!!,
                            SharedPreferenceUtil.TYPE_AUTH_TOKEN
                        )!!,
                        id!!,
                        product_name!!,
                        product_details!!,
                        product_code!!,
                        image_address!!,
                        id_unit!!,
                        sell_price!!.toDouble(),
                        supplier_price!!.toDouble(),
                        shopTypeId!!,
                        stock!!.toInt(),
                        stock!!.toInt(),
                        0.0,
                        currentDate!!,
                        status!!
                    )

                }
            }
        }
        return root
    }

    override fun unit(shop: MutableList<Unit>?) {
        unit = shop
        shop?.let {
            var mListUnitList: ArrayList<String>? = ArrayList<String>()
            for (unit in shop) {
                mListUnitList?.add(unit?.Name!!)
            }
            unitDataAdapter = ArrayAdapter<String>(
                activity as Activity,
                android.R.layout.simple_spinner_item,
                mListUnitList!!
            )
            unitDataAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_unit?.adapter = unitDataAdapter
            spinner_unit?.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        id_unit = shop.get(position).Id
                        Log.e("shop", "shop" + shop.get(position).Id)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // Another interface callback
                        Log.e("sdf", "S")
                    }

                }
        }
    }

    override fun shopType(shop: MutableList<ShopType>?) {
        shopType = shop
        shop?.let {
            var mListCompanyNameList: ArrayList<String>? = ArrayList<String>()
            for (company in shop) {
                mListCompanyNameList?.add(company?.Name!!)
            }
            shopTypeDataAdapter = ArrayAdapter<String>(
                activity as Activity,
                android.R.layout.simple_spinner_item,
                mListCompanyNameList!!
            )
            shopTypeDataAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_shop_type?.adapter = shopTypeDataAdapter
            spinner_shop_type?.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        shopTypeId = shop.get(position).Id
                        Log.e("shop", "shop" + shop.get(position).Id)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // Another interface callback
                        Log.e("sdf", "S")
                    }

                }
        }
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
            .into(img_background_mypage!!)
        img_user_add?.visibility = View.INVISIBLE
    }

    override fun show(value: String) {
        Toast.makeText(activity, value, Toast.LENGTH_LONG).show()
        if (activity is HomeActivity) {
            (activity as HomeActivity).onBackPressed()
        }
    }

    override fun failure(value: String) {
        Toast.makeText(activity, value, Toast.LENGTH_LONG).show()
    }

    override fun started() {
        progress_bar?.show()
    }

    override fun end() {
        progress_bar?.hide()
    }

}