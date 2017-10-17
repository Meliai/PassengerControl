package com.rudainc.passengercontrol.transport_layout;

import android.animation.Animator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.LinearLayout;

import com.rudainc.passengercontrol.R;

import java.util.ArrayList;
import java.util.List;

public class TransportLinearLayout extends LinearLayout {

    private static final int ELEMENTS_PER_ROW = 3;
    private final ArrayList<Transport> transportList = new ArrayList<>();
    private OnTransportChangeListener onTransportChangeListener;
    private List<TransportView> transportViewsList= new ArrayList<>();

    public TransportLinearLayout(Context context) {
        this(context, null, 0);
    }

    public TransportLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TransportLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        TypedArray icons = getResources().obtainTypedArray(R.array.transport_images);
        for (int i = 0; i < getResources().getStringArray(R.array.transport).length; i++) {
            transportList.add(new Transport(getResources().getStringArray(R.array.transport)[i], icons.getResourceId(i, -1)));
        }
        icons.recycle();
        initViews(context, transportList, ELEMENTS_PER_ROW);
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
                TransportView transportView = initView(context, transports.get(i));
                linearLayout.addView(transportView);
            }
        }
    }

    private TransportView initView(final Context context, final Transport transport) {
        final TransportView transportView = new TransportView(getContext());
        transportViewsList.add(transportView);
        transportView.setItem(transport);
        transportView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resetViews(context);
//                onTransportChangeListener.onChange(transport);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    int x = transportView.getWidth() / 2;
                    int y = transportView.getHeight() / 2;

                    int startRadius = 0;
                    int endRadius = (int) Math.hypot(transportView.getWidth(), transportView.getHeight());

                    Animator anim = ViewAnimationUtils.createCircularReveal(transportView, x, y, startRadius, endRadius);
                    transportView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
                    anim.start();
                }
                transportView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent));

            }
        });
        return transportView;
    }

    private void resetViews(Context context) {
        for (TransportView transportView : transportViewsList)
            transportView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite));

    }

    public void setOnTransportChangeListener(OnTransportChangeListener onTransportChangeListener) {
        this.onTransportChangeListener = onTransportChangeListener;
    }

    public interface OnTransportChangeListener {
        void onChange(Transport item);
    }

}
