package com.rudainc.passengercontrol.transport_layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.rudainc.passengercontrol.R;

import java.util.ArrayList;

public class TransportLinearLayout extends LinearLayout {

    private static final int ELEMENTS_PER_ROW = 3;
    private final ArrayList<Transport> transportViews = new ArrayList<>();

    public TransportLinearLayout(Context context) {
        this(context, null, 0);
    }

    public TransportLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TransportLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        TypedArray icons = getResources().obtainTypedArray(R.array.transport_image_inactive);
        for(int i = 0;i<getResources().getStringArray(R.array.transport).length;i++) {
            transportViews.add(new Transport(getResources().getStringArray(R.array.transport)[i], icons.getResourceId(i, -1)));
        }
        icons.recycle();
        initViews(context,transportViews,ELEMENTS_PER_ROW);
    }

    private void initViews(Context context, ArrayList<Transport> transports, int elementsPerRow) {
        LinearLayout linearLayout = null;
        for (int i = 0; i < transports.size(); i++) {
            if (i % elementsPerRow == 0) {
                linearLayout = new LinearLayout(context);
                linearLayout.setOrientation(HORIZONTAL);
                linearLayout.setWeightSum(elementsPerRow);
                addView(linearLayout);
            }
            if (linearLayout != null) {
                TransportView transportView = initView(transports.get(i));
                linearLayout.addView(transportView);
            }
        }
    }

    private TransportView initView(Transport transport) {
        TransportView intervalView = new TransportView(getContext());
        intervalView.setItem(transport);
        return intervalView;
    }

}
