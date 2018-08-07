package com.toolslab.tractorlocator.base_mvp

interface BaseView : MvpView {
    fun showError(message: String?)

    fun showNoConnectionError()
}
