package com.kotlindemo.activity.otherthings.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
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
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.astritveliu.boom.Boom
import com.kotlindemo.R
import com.kotlindemo.fragments.DemoDialogFragment
import com.kotlindemo.utility.Inject
import com.kotlindemo.utility.ToastManager
import kotlinx.android.synthetic.main.activity_auto_textview.*
import kotlinx.android.synthetic.main.activity_my_test_demo.toolbar

/**
 * Created by Manish Patel on 9/6/2019.
 */
//https://professorneurus.wordpress.com/2013/10/23/adding-multiple-clicking-regions-to-an-android-textview/
//https://blog.stylingandroid.com/the-trouble-with-clickablespan/
//https://android--examples.blogspot.com/2016/08/android-clickablespan-example.html

class MyTestDemo : ParentActivity() {

    val DEFAULT_INTERPOLATOR = AccelerateDecelerateInterpolator()

    val POP_OUT_SCALE_X = 1.1f
    val POP_OUT_SCALE_Y = 1.1f
    val PUSH_IN_ANIM_DURATION = 200
    val POP_OUT_ANIM_DURATION = 200

    private var popOutScaleX = POP_OUT_SCALE_X
    private var popOutScaleY = POP_OUT_SCALE_Y
    private var pushInAnimDuration = PUSH_IN_ANIM_DURATION
    private var popOutAnimDuration = POP_OUT_ANIM_DURATION

    private var pushInInterpolator = DEFAULT_INTERPOLATOR
    private var popOutInterpolator = DEFAULT_INTERPOLATOR

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_test_demo)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Boom(btn_report_sos as View)

        //bounce animation
        val bounce_5 = AnimationUtils.loadAnimation(this, R.anim.bounce_5)

        btn_report_sos.setOnClickListener {
            /*startAnimScale(
                btn_report_sos,
                pushInScaleX,
                pushInScaleY,
                pushInAnimDuration,
                pushInInterpolator,
                0
            )*/

            startAnimScale(btn_report_sos, popOutScaleX, popOutScaleY, popOutAnimDuration, popOutInterpolator, 0)

            startAnimScale(
                btn_report_sos,
                1f,
                1f,
                popOutAnimDuration,
                popOutInterpolator,
                popOutAnimDuration + 1
            )

            //btn_report_sos.startAnimation(bounce_5)
        }

        btn_dialog.setOnClickListener {
            //showDialog()
        }

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
        val broadCastTitleList = Inject.getBroadcastTitleList()

        var start = 0
        var end = 0

        for (broadCastTitle in broadCastTitleList) {
            //println("broadCastTitle $broadCastTitle")

            end = start + broadCastTitle.length

            if (start < end) {
                spannableBuilder.append("$broadCastTitle  -  ")
                spannableBuilder.setSpan(
                    MyClickableSpan(broadCastTitle),
                    start,
                    end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            start += broadCastTitle.length
        }

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

        //4th way
        /*text_title.makeLinks(
            Pair("Terms of Service", View.OnClickListener {
                Toast.makeText(applicationContext, "Terms of Service Clicked", Toast.LENGTH_SHORT).show()
            }),
            Pair("Privacy Policy", View.OnClickListener {
                Toast.makeText(applicationContext, "Privacy Policy Clicked", Toast.LENGTH_SHORT).show()
            }))*/

        text_title.text = spannableBuilder

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

            override fun onAnimationEnd(anim: Animation) {
                Log.e(TAG, "Animation End")
                println("Animation End")
                //text_title.text ="testing"
                //anim.repeatCount = 1
                //text_title.startAnimation(marquee)
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
            spannableString.setSpan(
                clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        this.movementMethod =
            LinkMovementMethod.getInstance() // without LinkMovementMethod, link can not click
        this.setText(spannableString, TextView.BufferType.SPANNABLE)
    }

    private inner class MyClickableSpan constructor(private val mText: String) :
        ClickableSpan() {
        override fun onClick(widget: View) {
            ToastManager.getInstance().showToast("$mText")
            //mListener.onTagClicked(mText)
        }

        override fun updateDrawState(textPaint: TextPaint) {
            textPaint.isUnderlineText = false    // this remove the underline
        }
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


    private fun startAnimScale(
        view: View, scaleX: Float, scaleY: Float,
        animDuration: Int,
        interpolator: AccelerateDecelerateInterpolator,
        startDelay: Int
    ) {
        val animX = ObjectAnimator.ofFloat(view, "scaleX", scaleX)
        val animY = ObjectAnimator.ofFloat(view, "scaleY", scaleY)
        val animatorSet = AnimatorSet()
        animX.duration = animDuration.toLong()
        animX.interpolator = interpolator
        animY.duration = animDuration.toLong()
        animY.interpolator = interpolator

        animatorSet.playTogether(animX, animY)
        animatorSet.startDelay = startDelay.toLong()
        animatorSet.start()
    }


    companion object {
        val TAG: String = MyTestDemo.javaClass::class.java.simpleName
    }
}