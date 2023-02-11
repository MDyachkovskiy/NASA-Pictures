package com.gb_materialdesign.ui.main.AppState

import android.view.View
import com.gb_materialdesign.R
import com.gb_materialdesign.utils.showSnackbar

class AppStateRenderer (
    private val parentView: View,
    private val action: (View) -> Unit
) {
    fun render (appState: AppState) {
        val loadindLayout = parentView.findViewById<View>(R.id.includedLoadingLayout)

        if(loadindLayout != null) {
            when (appState) {
                is AppState.Error -> {
                loadindLayout.visibility = View.GONE
                    parentView.showSnackbar(
                        parentView.context.getString(R.string.requestError),
                        parentView.context.getString(R.string.repeatRequest),
                        action, 0)
                }
                AppState.Loading -> {
                    loadindLayout.visibility = View.VISIBLE
                }
                is AppState.Success -> {
                    loadindLayout.visibility = View.GONE
                }
            }
        }
    }
}