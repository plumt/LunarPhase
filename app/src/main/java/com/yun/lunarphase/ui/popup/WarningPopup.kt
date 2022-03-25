package com.yun.lunarphase.ui.popup

import android.app.AlertDialog
import android.content.Context
import android.view.View
import com.google.android.material.button.MaterialButton
import com.yun.lunarphase.R
import com.yun.lunarphase.BR
import com.yun.lunarphase.databinding.DialogWarningBinding

class WarningPopup {

    lateinit var customDialogListener: CustomDialogListener

    fun showPopup(context: Context, title: String, contents: String){
        AlertDialog.Builder(context).run {
            setCancelable(false)
            val view = View.inflate(context, R.layout.dialog_warning, null)
            val binding = DialogWarningBinding.bind(view)
            binding.setVariable(BR.title, title)
            binding.setVariable(BR.contents, contents)
            setView(binding.root)
            val dialog = create()

//            dialog.setOnDismissListener {
//                customDialogListener.onResultClicked(false)
//            }

            // 확인 버튼
            view.findViewById<MaterialButton>(R.id.btn_result).setOnClickListener {
                customDialogListener.onResultClicked(true)
                dialog.dismiss()
            }
            dialog
        }.show()


    }

    interface CustomDialogListener{
        fun onResultClicked(result: Boolean)
    }

    fun setDialogListener(customDialogListener: CustomDialogListener){
        this.customDialogListener = customDialogListener
    }
}