package com.gb_materialdesign.ui.main.appState

import android.view.View
import com.gb_materialdesign.R
import com.gb_materialdesign.utils.showSnackbar

class AppStateRenderer (
    private val parentView: View,
    private val action: (View) -> Unit
) {
    fun render (appState: com.test.application.core.utils.AppState?) {
        val loadindLayout = parentView.findViewById<View>(R.id.includedLoadingLayout)

        if(loadindLayout != null) {
            when (appState) {
                is com.test.application.core.utils.AppState.Error -> {
                loadindLayout.visibility = View.GONE
                    parentView.showSnackbar(
                        appState.error.toString(),
                        parentView.context.getString(R.string.repeatRequest),
                        action, 0)
                }
                com.test.application.core.utils.AppState.Loading -> {
                    loadindLayout.visibility = View.VISIBLE
                }

                else -> loadindLayout.visibility = View.GONE
            }
        }
    }
}