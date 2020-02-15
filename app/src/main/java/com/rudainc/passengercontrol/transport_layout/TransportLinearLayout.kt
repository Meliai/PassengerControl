package com.rudainc.passengercontrol.transport_layout

import android.animation.Animator
import android.content.Context
import android.content.res.TypedArray
import androidx.core.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.LinearLayout

import com.rudainc.passengercontrol.R

import java.util.ArrayList

class TransportLinearLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {
    private val transportList = ArrayList<Transport>()
    private var onTransportChangeListener: OnTransportChangeListener? = null
    private val transportViewsList = ArrayList<TransportView>()

    init {
        orientation = LinearLayout.VERTICAL
        val icons = resources.obtainTypedArray(R.array.transport_images)
        for (i in 0 until resources.getStringArray(R.array.transport).size) {
            transportList.add(Transport(resources.getStringArray(R.array.transport)[i], icons.getResourceId(i, -1)))
        }
        icons.recycle()
        initViews(context, transportList, ELEMENTS_PER_ROW)
    }

    private fun initViews(context: Context, transports: ArrayList<Transport>, elementsPerRow: Int) {
        var linearLayout: LinearLayout? = null
        for (i in transports.indices) {
            if (i % elementsPerRow == 0) {
                linearLayout = LinearLayout(context)
                linearLayout.orientation = LinearLayout.HORIZONTAL
                linearLayout.weightSum = elementsPerRow.toFloat()
                addView(linearLayout)
            }
            if (linearLayout != null) {
                val transportView = initView(context, transports[i], i == 0)
                linearLayout.addView(transportView)
            }
        }
    }

    private fun initView(context: Context, transport: Transport, isFirst: Boolean): TransportView {
        val transportView = TransportView(getContext())
        transportViewsList.add(transportView)
        transportView.setItem(transport)
        if (isFirst)
            transportView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
        transportView.setOnClickListener {
            resetViews(context)
            onTransportChangeListener!!.onChange(transport)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                val x = transportView.width / 2
                val y = transportView.height / 2

                val startRadius = 0
                val endRadius = Math.hypot(transportView.width.toDouble(), transportView.height.toDouble()).toInt()

                val anim = ViewAnimationUtils.createCircularReveal(transportView, x, y, startRadius.toFloat(), endRadius.toFloat())
                transportView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent))
                anim.start()
            }
            transportView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
        }
        return transportView
    }

    private fun resetViews(context: Context) {
        for (transportView in transportViewsList)
            transportView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite))

    }

    fun setOnTransportChangeListener(onTransportChangeListener: OnTransportChangeListener) {
        this.onTransportChangeListener = onTransportChangeListener
    }

    interface OnTransportChangeListener {
        fun onChange(item: Transport)
    }

    companion object {

        private val ELEMENTS_PER_ROW = 3
    }

}
