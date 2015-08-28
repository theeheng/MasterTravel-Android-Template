package com.mastercard.travel.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import com.mastercard.travel.R;


/**
 * Created by ignacio on 08/02/2015.
 */
public class TwoLinesTextView extends TextView {

    private String title;

    private String subtitle;

    private SpannableStringBuilder spanStringBuilder;

    private AbsoluteSizeSpan firtsLineSize;
    private ForegroundColorSpan firtsLineColor;
    private ForegroundColorSpan secondLineColor;

    public TwoLinesTextView(Context context) {
        this(context, null);
    }

    public TwoLinesTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TwoLinesTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.TwoLinesTextView, 0, 0);

        try {
            title = a.getString(R.styleable.TwoLinesTextView_firstLine);
            subtitle = a.getString(R.styleable.TwoLinesTextView_secondLine);
        } finally {
            a.recycle();
        }

        init();
    }

    private void init() {

        spanStringBuilder = new SpannableStringBuilder();

        firtsLineSize = new AbsoluteSizeSpan(getResources().getDimensionPixelSize(R.dimen.title_text_size));

        firtsLineColor = new ForegroundColorSpan(Color.DKGRAY);
        secondLineColor = new ForegroundColorSpan(Color.BLACK);

        initText();
    }

    private void initText() {

        if (title == null || subtitle == null)
            return;

        spanStringBuilder.clear();

        spanStringBuilder.append(title).
                append("\n").
                append(subtitle);

        spanStringBuilder.setSpan(firtsLineColor, 0, title.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spanStringBuilder.setSpan(firtsLineSize, 0, title.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        spanStringBuilder.setSpan(secondLineColor, title.length() + 1, spanStringBuilder.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        setText(spanStringBuilder, BufferType.SPANNABLE);
    }

    public void setTitle(String title) {
        this.title = title;

        initText();
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;

        initText();
    }

    public void setSubtitle(int resourceId) {
        setSubtitle(getContext().getResources().getString(resourceId));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(int resourceId) {
        setTitle(getContext().getResources().getString(resourceId));
    }

    public String getSubttile() {
        return subtitle;
    }
}
