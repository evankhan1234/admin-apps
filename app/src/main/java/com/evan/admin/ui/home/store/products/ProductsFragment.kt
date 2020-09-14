package com.evan.admin.ui.home.store.products

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.evan.admin.R
import com.evan.admin.data.db.entities.Product
import com.evan.admin.ui.home.HomeActivity
import com.evan.admin.util.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

private const val categoryId = "foo"

class ProductsFragment : Fragment() , KodeinAware,IProductListener,IProductViewListener {
    override val kodein by kodein()

    private val factory : ProductModelFactory by instance()
    var rcv_search: RecyclerView?=null
    var producteAdapter: ProductAdapter?=null
    var adapter_search: ProductSearchAdapter?=null
    var edit_content: EditText?=null
    var rcv_product: RecyclerView?=null
    var progress_bar: ProgressBar?=null
    var btn_product_new: ImageView?=null
    var token:String?=""
    private lateinit var viewModel: ProductViewModel
    companion object {
        var currentCategoryId = 0
        fun newInstance(foo: Int) = ProductsFragment().apply {
            arguments = bundleOf(
                categoryId to foo
            )
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_products, container, false)
        rcv_search=root?.findViewById(R.id.rcv_search)
        progress_bar=root?.findViewById(R.id.progress_bar)
        rcv_product=root?.findViewById(R.id.rcv_product)
        btn_product_new=root?.findViewById(R.id.btn_product_new)
        edit_content=root?.findViewById(R.id.edit_content)
        edit_content?.addTextChangedListener(keyword)
        viewModel = ViewModelProviders.of(this, factory).get(ProductViewModel::class.java)
        viewModel.listener=this
        token = SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)


        btn_product_new?.setOnClickListener {
            if (activity is HomeActivity) {
               (activity as HomeActivity).goToCreateProductFragment()
            }
        }
        return root;
    }

    override fun onUpdate(product: Product) {
        if (activity is HomeActivity) {
          (activity as HomeActivity).goToUpdateProductFragment(product)
        }
    }

    fun replace(){
        viewModel.replaceSubscription(this)
        startListening()
    }
    override fun onResume() {
        super.onResume()
        // viewModel.getCategoryType(token!!)

        replace()
        initAdapter()
        initState()
        Log.e("stop","stop")

        arguments?.let {
            val catId = it.getInt(categoryId)
            currentCategoryId = catId
            Log.e("mEventCategoryId: ", catId.toString())
        }
    }

    private fun initAdapter() {
        producteAdapter = ProductAdapter(context!!,this)
        rcv_product?.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rcv_product?.adapter = producteAdapter
        startListening()
    }

    private fun startListening() {
        rcv_search?.visibility=View.GONE
        rcv_product?.visibility=View.VISIBLE
        viewModel.listOfAlerts?.observe(this, Observer {
            producteAdapter?.submitList(it)
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


    override fun show(data: MutableList<Product>) {
        rcv_search?.visibility=View.VISIBLE
        rcv_product?.visibility=View.GONE
        viewModel.replaceSubscription(this)
        adapter_search = ProductSearchAdapter(context!!,data!!,this)
        rcv_search?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
            adapter = adapter_search
        }
    }

    override fun started() {
        progress_bar?.show()
    }

    override fun end() {
        progress_bar?.hide()
    }

    var keyword: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun afterTextChanged(s: Editable) {

            try {
                if (s.toString().equals("")){
                    startListening()
                }
                else{
                    var keyword:String?=""
                    keyword=s.toString()+"%"
                    viewModel.getSystemList(token!!,keyword,currentCategoryId)
                }

            } catch (e: Exception) {

            }


        }

    }
    override fun exit(){
        rcv_search?.visibility=View.GONE
        rcv_product?.visibility=View.GONE
        viewModel.replaceSubscription(this)
    }

}