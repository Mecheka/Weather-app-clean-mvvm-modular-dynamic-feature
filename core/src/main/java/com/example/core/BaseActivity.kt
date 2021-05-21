package com.example.core

import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    private val loadingDialog: DialogLoading by lazy { DialogLoading() }

    open fun showLoadingDialog() {
        loadingDialog.isCancelable = false
        loadingDialog.show(supportFragmentManager, "Loading dialog")
    }

    open fun hideLoadingDialog() {
        loadingDialog.dismiss()
    }
}