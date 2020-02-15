package com.rudainc.passengercontrol.transport_layout

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.cardview.widget.CardView
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.rudainc.passengercontrol.R

class TransportView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.style.mCardView) : CardView(context, attrs, R.style.mCardView) {

    private var mTextViewTitle: TextView? = null

    private var mImageView: ImageView? = null

    private val margin = 8
    private lateinit var ll: LinearLayout

    init {

        initContent(context)

        val param = LinearLayout.LayoutParams(
                0,
                FrameLayout.LayoutParams.MATCH_PARENT
        )

        param.weight = 1f
        setPadding(margin, margin, margin, margin)
        param.setMargins(margin, margin, margin, margin)

        layoutParams = param
    }

    private fun initContent(context: Context) {
        ll = LinearLayout(context)

        ll.orientation = LinearLayout.VERTICAL

        initImage(context)
        initTitle(context)

        addView(ll)
    }

    private fun initTitle(context: Context) {
        mTextViewTitle = TextView(context)
        val param = LinearLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        )

        mTextViewTitle?.gravity = Gravity.CENTER
        mTextViewTitle?.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.text_size_16))
        mTextViewTitle?.setTextColor(ContextCompat.getColor(context, R.color.colorBlack))
        param.weight = 1f
        param.setMargins(margin, margin, margin, 32)
        mTextViewTitle?.layoutParams = param

        ll.addView(mTextViewTitle)
    }


    private fun initImage(context: Context) {
        mImageView = ImageView(context)
        val param = LinearLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        )
        param.weight = 3f
        param.setMargins(0, 32, 0, 0)
        //        setPadding(margin, margin, margin, margin);
        mImageView?.layoutParams = param

        ll.addView(mImageView)

    }


    fun setItem(transport: Transport) {
        mImageView?.setImageResource(transport.image)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }
}
