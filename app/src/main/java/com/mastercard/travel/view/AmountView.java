package com.mastercard.travel.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.mastercard.travel.R;


/**
 * Created by ignacio on 08/02/2015.
 */
public class AmountView extends RelativeLayout {

    private String title;

    private int amount;

    private int icon;

    private TwoLinesTextView twoLinesTextView;

    private ImageButton addButton;

    private ImageButton removeButton;

    private OnAmountChangeListener listener;

    public AmountView(Context context) {
        this(context, null);
    }

    public AmountView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AmountView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.AmountView, 0, 0);

        try {
            title = a.getString(R.styleable.AmountView_firstLine);
            icon = a.getResourceId(R.styleable.AmountView_icon1, 0);
            amount = a.getInteger(R.styleable.AmountView_amount1, 1);
        } finally {
            a.recycle();
        }

        init();
    }

    private void init() {

        LayoutInflater.from(getContext()).inflate(R.layout.amount_view, this, true);

        twoLinesTextView = (TwoLinesTextView) findViewById(R.id.text);
        twoLinesTextView.setTitle(title);
        twoLinesTextView.setSubtitle(String.valueOf(amount));
        twoLinesTextView.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0);
        twoLinesTextView.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.text_drawable_padding));

        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (removeButton == v)
                    amount -= amount > 1 ? 1 : 0;
                else
                    amount++;

                notifyDataChanged();
            }
        };

        addButton = (ImageButton) findViewById(R.id.add_button);
        addButton.setOnClickListener(listener);

        removeButton = (ImageButton) findViewById(R.id.remove_button);
        removeButton.setOnClickListener(listener);
    }

    private void notifyDataChanged() {

        twoLinesTextView.setSubtitle(String.valueOf(amount));

        if (listener != null)
            listener.onAmountChange(amount);
    }

    public OnAmountChangeListener getListener() {
        return listener;
    }

    public void setListener(OnAmountChangeListener listener) {
        this.listener = listener;
    }

    public int getAmount() {
        return amount;
    }

    public interface OnAmountChangeListener {
        public void onAmountChange(int amount);
    }
}
