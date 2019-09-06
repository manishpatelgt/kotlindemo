package com.kotlindemo.activity.otherthings.ui

import android.graphics.Color
import android.os.Bundle
import android.text.*
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import com.kotlindemo.application.ParentActivity
import kotlinx.android.synthetic.main.activity_my_test_demo.*
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.kotlindemo.R
import com.kotlindemo.utility.Inject
import com.kotlindemo.utility.ToastManager

/**
 * Created by Manish Patel on 9/6/2019.
 */
class MyTestDemo : ParentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_test_demo)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        text_title.highlightColor = Color.TRANSPARENT
        text_title.linksClickable = true
        text_title.isClickable = true
        text_title.movementMethod = LinkMovementMethod.getInstance()

        val spannableBuilder = SpannableStringBuilder()

        //1st way
        /*val broadCastTitle = "RAIN INCOMING FROM NORTHWEST INCLUDING"

        spannableBuilder.append(broadCastTitle)
        spannableBuilder.setSpan(
            clickableSpan,
            spannableBuilder.length - broadCastTitle.length,
            spannableBuilder.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )*/

        //2nd way
        /*val broadCastTitleList = Inject.getBroadcastTitleList()

        var start = 0
        var end = 0

        for (broadCastTitle in broadCastTitleList) {
            println("broadCastTitle $broadCastTitle")

            end = start + broadCastTitle.length

            if (start < end) {
                spannableBuilder.append(broadCastTitle)

                spannableBuilder.setSpan(
                    clickableSpan,
                    start,
                    end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            start += broadCastTitle.length
        }*/

        //3rd way
        //https://stackoverflow.com/questions/19227276/how-can-i-make-several-clickable-parts-of-text-in-textview
        /*val broadCastTitle = "RAIN INCOMING FROM NORTHWEST INCLUDING"

        spannableBuilder.append(broadCastTitle)
        spannableBuilder.setSpan(
            object : ClickableSpan() {
                override fun onClick(view: View) {
                    //what happens when i click
                    ToastManager.getInstance().showToast(broadCastTitle)
                }

                override fun updateDrawState(textPaint: TextPaint) {
                    textPaint.isUnderlineText = false    // this remove the underline
                }
            },
            0,
            spannableBuilder.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )*/

        //text_title.text = spannableBuilder.toString()

        //4th way
        text_title.makeLinks(
            Pair("Terms of Service", View.OnClickListener {
                Toast.makeText(applicationContext, "Terms of Service Clicked", Toast.LENGTH_SHORT).show()
            }),
            Pair("Privacy Policy", View.OnClickListener {
                Toast.makeText(applicationContext, "Privacy Policy Clicked", Toast.LENGTH_SHORT).show()
            }))

        //marquee animation
        val marquee = AnimationUtils.loadAnimation(this, R.anim.marquee)

        text_title.startAnimation(marquee)

        //marquee AnimationListener
        marquee.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(arg0: Animation) {
                println("Animation Started")
            }

            override fun onAnimationRepeat(arg0: Animation) {
                Log.e(TAG, "Animation Repeat")
                println("Animation Repeat")
            }

            override fun onAnimationEnd(arg0: Animation) {
                Log.e(TAG, "Animation End")
                println("Animation End")
            }
        })

        //setClickable(textView, subString, {handleClick()})
    }

    //https://stackoverflow.com/questions/19227276/how-can-i-make-several-clickable-parts-of-text-in-textview
    //https://stackoverflow.com/questions/10696986/how-to-set-the-part-of-the-text-view-is-clickable?rq=1
    fun TextView.makeLinks(vararg links: Pair<String, View.OnClickListener>) {
        val spannableString = SpannableString(this.text)
        for (link in links) {
            val clickableSpan = object : ClickableSpan() {
                override fun onClick(view: View) {
                    Selection.setSelection((view as TextView).text as Spannable, 0)
                    view.invalidate()
                    link.second.onClick(view)
                }
            }
            val startIndexOfLink = this.text.toString().indexOf(link.first)
            spannableString.setSpan(clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        this.movementMethod = LinkMovementMethod.getInstance() // without LinkMovementMethod, link can not click
        this.setText(spannableString, TextView.BufferType.SPANNABLE)
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