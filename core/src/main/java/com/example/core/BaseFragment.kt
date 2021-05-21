package com.example.core

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.viewbinding.ViewBinding
import com.example.core.common.DataEntity

abstract class BaseFragment<B : ViewBinding> : Fragment() {

    lateinit var binding: B
    protected abstract fun setupBinding(): B
    private var loadingDialog: DialogLoading? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = setupBinding()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }

    protected fun <T> LiveData<DataEntity<T>>.observerDataEntity(
        lifecycle: LifecycleOwner,
        action: (data: T?) -> Unit
    ) {
        this.observe(lifecycle) {
            when (it) {
                is DataEntity.ERROR -> {
                    hideLoadingDialog()
                    AlertDialog.Builder(requireContext())
                        .setTitle("Error")
                        .setMessage(it.error.message)
                        .setPositiveButton("ok") { dialog, _ ->
                            dialog.dismiss()
                        }.show()
                }
                is DataEntity.LOADING -> showLoadingDialog()
                is DataEntity.SUCCESS -> {
                    hideLoadingDialog()
                    action(it.data)
                }
            }
        }
    }

    open fun showLoadingDialog() {
        loadingDialog = DialogLoading()
        loadingDialog?.isCancelable = false
        loadingDialog?.show(childFragmentManager, "Loading dialog")
    }

    open fun hideLoadingDialog() {
        loadingDialog?.dismiss()
    }
}