package com.mastercard.travel.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mastercard.travel.R;


/**
 * Created by ignacio on 10/02/15.
 */
public class TwoLinesEditText extends RelativeLayout {

    private String title;

    private String subtitle;

    private TextView titleView;

    private EditText editText;

    public TwoLinesEditText(Context context) {
        this(context, null);
    }

    public TwoLinesEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TwoLinesEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.TwoLinesEditText, 0, 0);

        try {
            title = a.getString(R.styleable.TwoLinesEditText_firstLine);
            subtitle = a.getString(R.styleable.TwoLinesEditText_secondLine);
        } finally {
            a.recycle();
        }

        init();
    }

    private void init() {

        LayoutInflater.from(getContext()).inflate(R.layout.two_lines_edit_text, this, true);

        titleView = (TextView) findViewById(R.id.title);

        if (title != null)
            titleView.setText(title);

        editText = (EditText) findViewById(R.id.text);

        if (subtitle != null)
            editText.setText(subtitle);

    }

    public void setText(String text) {
        subtitle = text;

        editText.setText(subtitle);
    }

    public void setEditable(boolean editable) {
        editText.setFocusable(false);
    }
}
