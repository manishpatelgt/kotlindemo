package com.kotlindemo.fragments

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.kotlindemo.R
import com.kotlindemo.activity.otherthings.ui.AutoTextViewDemoActivity
import com.kotlindemo.application.DemoApplication
import kotlinx.android.synthetic.main.dialog_layout.*
import org.slf4j.LoggerFactory

/**
 * Created by Manish Patel on 9/11/2019.
 */
class DemoDialogFragment : DialogFragment() {

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window.setLayout(width, height)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_layout, container, false)
        dialog?.requestWindowFeature(STYLE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isCancelable = false
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        button_confirm_break.setOnClickListener { v ->
            dismissAllowingStateLoss()
        }

        button_extend_break.setOnClickListener { v ->

            activity?.let {
                //Go to AutoTextViewDemoActivity Activity
                val intent = Intent(it, AutoTextViewDemoActivity::class.java)
                //intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                it.startActivity(intent)
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(DemoDialogFragment::class.java)
        fun newInstance(): DemoDialogFragment {
            return DemoDialogFragment()
        }
    }
}
