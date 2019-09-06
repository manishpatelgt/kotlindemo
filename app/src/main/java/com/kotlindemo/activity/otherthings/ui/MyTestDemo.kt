package com.kotlindemo.activity.otherthings.ui

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.kotlindemo.application.ParentActivity
import kotlinx.android.synthetic.main.activity_my_test_demo.*
import android.text.TextPaint
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.kotlindemo.R
import com.kotlindemo.activity.MainActivity
import com.kotlindemo.utility.ToastManager
import com.kotlindemo.utility.getMarquee

/**
 * Created by Manish Patel on 9/6/2019.
 */
class MyTestDemo : ParentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.kotlindemo.R.layout.activity_my_test_demo)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        text_title.highlightColor = Color.TRANSPARENT
        text_title.linksClickable = true
        text_title.isClickable = true
        text_title.movementMethod = LinkMovementMethod.getInstance()

        val broadCastTitle = "RAIN INCOMING FROM NORTHWEST INCLUDING"
        val spannableBuilder = SpannableStringBuilder()

        spannableBuilder.append(broadCastTitle)
        spannableBuilder.setSpan(
            clickableSpan,
            spannableBuilder.length - broadCastTitle.length,
            spannableBuilder.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        text_title.text = spannableBuilder
        //marquee animation
        val marquee = AnimationUtils.loadAnimation(this, R.anim.marquee)

        //marquee AnimationListener
        marquee.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(arg0: Animation) {}
            override fun onAnimationRepeat(arg0: Animation) {
                Log.e(TAG, "Animation End")
            }

            override fun onAnimationEnd(arg0: Animation) {
                Log.e(TAG, "Animation End")
            }
        })

        text_title.startAnimation(this@MyTestDemo.getMarquee())

        //setClickable(textView, subString, {handleClick()})
    }

    //adding click span
    var clickableSpan: ClickableSpan = object : ClickableSpan() {
        override fun onClick(view: View) {
            //what happens when i click
            ToastManager.getInstance().showToast("you just clicked on a Click Span!")
        }

        override fun updateDrawState(textPaint: TextPaint) {
            textPaint.isUnderlineText = false    // this remove the underline
        }
    }

    fun setClickable(
        textView: TextView,
        subString: String,
        handler: () -> Unit,
        drawUnderline: Boolean = false
    ) {
        val text = textView.text
        val start = text.indexOf(subString)
        val end = start + subString.length

        val span = SpannableString(text)
        span.setSpan(
            ClickHandler(handler, drawUnderline),
            start,
            end,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        textView.linksClickable = true
        textView.isClickable = true
        textView.movementMethod = LinkMovementMethod.getInstance()

        textView.text = span
    }

    class ClickHandler(
        private val handler: () -> Unit,
        private val drawUnderline: Boolean
    ) : ClickableSpan() {
        override fun onClick(widget: View?) {
            handler()
        }

        override fun updateDrawState(ds: TextPaint?) {
            if (drawUnderline) {
                super.updateDrawState(ds)
            } else {
                ds?.isUnderlineText = false
            }
        }
    }

    companion object {
        val TAG: String = MyTestDemo.javaClass::class.java.simpleName
    }
}