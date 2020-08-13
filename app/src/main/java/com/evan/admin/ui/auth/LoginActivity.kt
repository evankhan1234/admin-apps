package com.evan.admin.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.evan.admin.ui.home.HomeActivity
import kotlinx.android.synthetic.main.activity_login.*
import com.evan.admin.R
import com.evan.admin.data.db.entities.User
import com.evan.admin.databinding.ActivityLoginBinding
import com.evan.admin.util.MyPasswordTransformationMethod
import com.evan.admin.util.hide
import com.evan.admin.util.show
import com.evan.admin.util.snackbar
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class LoginActivity : AppCompatActivity(), AuthListener, KodeinAware {

    override val kodein by kodein()
    private val factory : AuthViewModelFactory by instance()
    var text_building_name: String? = ""
    var tv_sign_in: TextView? = null
    var et_email: EditText? = null
    var et_mobile: EditText? = null
    var radio_email: RadioButton? = null
    var radio_mobile: RadioButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.authListener = this

        et_password?.transformationMethod = MyPasswordTransformationMethod()

        show_pass?.setOnClickListener {
            onPasswordVisibleOrInvisible()
        }
        text_building_name =
            resources!!.getString(R.string.account) + "<font color=#DDC915> Sign up</font>"
        tv_sign_in?.text = Html.fromHtml(text_building_name)

    }
    fun onPasswordVisibleOrInvisible() {
        val cursorPosition = et_password?.selectionStart

        if (et_password?.transformationMethod == null) {
            et_password?.transformationMethod = MyPasswordTransformationMethod()
            show_pass?.isSelected = false
        } else {

            et_password?.transformationMethod = null
            show_pass?.isSelected = true
        }
        et_password?.setSelection(cursorPosition!!)
    }
    override fun onStarted() {
        progress_bar.show()
    }

    override fun onSuccess(user: User) {
        progress_bar.hide()
        Intent(this, HomeActivity::class.java).also {
            startActivity(it)
            finish()
        }

    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }

}
