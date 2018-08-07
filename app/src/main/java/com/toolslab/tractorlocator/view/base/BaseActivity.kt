package com.toolslab.tractorlocator.view.base

import android.annotation.SuppressLint
import android.widget.Toast
import com.toolslab.tractorlocator.R
import com.toolslab.tractorlocator.base_mvp.BaseView
import dagger.android.support.DaggerAppCompatActivity

@SuppressLint("Registered") // BaseActivity should not go in the manifest
open class BaseActivity : DaggerAppCompatActivity(), BaseView {

    override fun showNoConnectionError() {
        Toast.makeText(this, R.string.no_internet, Toast.LENGTH_SHORT).show()
    }

    override fun showError(message: String?) {
        val error = message ?: getString(R.string.unknown_error)
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

}
