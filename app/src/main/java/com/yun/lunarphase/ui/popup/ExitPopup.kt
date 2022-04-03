package com.yun.lunarphase.ui.popup

import android.app.AlertDialog
import android.content.Context
import android.view.View
import com.google.android.material.button.MaterialButton
import com.yun.lunarphase.BR
import com.yun.lunarphase.R
import com.yun.lunarphase.databinding.DialogExitBinding

class ExitPopup{
    lateinit var customDialogListener: CustomDialogListener

    fun showPopup(context: Context, title: String, contents: String){
        AlertDialog.Builder(context).run {
            setCancelable(true)
            val view = View.inflate(context, R.layout.dialog_exit, null)
            val binding = DialogExitBinding.bind(view)
            binding.setVariable(BR.title, title)
            binding.setVariable(BR.contents, contents)
            setView(binding.root)
            val dialog = create()

//            dialog.setOnDismissListener {
//                customDialogListener.onResultClicked(false)
//            }

            // 종료 버튼
            view.findViewById<MaterialButton>(R.id.btn_exit).setOnClickListener {
                customDialogListener.onResultClicked(true)
                dialog.dismiss()
            }
            // 취소 버튼
            view.findViewById<MaterialButton>(R.id.btn_cancel).setOnClickListener {
                customDialogListener.onResultClicked(false)
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