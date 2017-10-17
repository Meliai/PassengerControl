package com.rudainc.passengercontrol.transport_layout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rudainc.passengercontrol.R;

public class TransportView extends CardView {

    private TextView mTextViewTitle;

    private ImageView mImageView;


    private int margin = 16;
    private int ELEMENTS_IN_VIEW = 3;
    LinearLayout ll;


    public TransportView(Context context) {
        this(context, null, R.style.mCardView);
    }

    public TransportView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.style.mCardView);
    }

    public TransportView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, R.style.mCardView);

        initContent(context);

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                0,
                LayoutParams.MATCH_PARENT
        );

        param.weight = 1;
        setPadding(margin, margin, margin, margin);
        param.setMargins(margin, margin, margin, margin);

        setLayoutParams(param);
    }

    private void initContent(Context context) {
        ll = new LinearLayout(context);

        ll.setOrientation(LinearLayout.VERTICAL);

        initImage(context);
        initTitle(context);

        addView(ll);
    }

    private void initTitle(Context context) {
        mTextViewTitle = new TextView(context);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        mTextViewTitle.setGravity(Gravity.CENTER);
        mTextViewTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.text_size_16));
        mTextViewTitle.setTextColor(ContextCompat.getColor(context,R.color.colorBlack));
        mTextViewTitle.setLayoutParams(param);

        ll.addView(mTextViewTitle);
    }


    private void initImage(Context context) {
        mImageView = new ImageView(context);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
               0
        );
        param.weight = 1;
        mImageView.setLayoutParams(param);

        ll.addView(mImageView);

    }


    public void setItem(Transport transport)  {
        mImageView.setImageResource(transport.getImage());
        mTextViewTitle.setText(transport.getTitle());
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }
}
