package com.viciy.scrolltableview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * Created by bai_qiang.yang
 * 2017/7/24 15:45
 * Mail: viciyforever@gmail.com
 * DC:只显示一行的TextView，长度过长会自动缩放字体
 */

public class SingleLineTextView extends TextView {
    public SingleLineTextView(Context context) {
        this(context,null);
    }

    public SingleLineTextView(Context context, @Nullable AttributeSet attrs) {
        this(context,null,0);
    }

    public SingleLineTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setSingleLine();
        setEllipsize(TextUtils.TruncateAt.END);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final Layout layout = getLayout();
        if (null != layout) {
            final int lineCount = layout.getLineCount();
            if (lineCount > 0) {
                final int ellipsisCount = layout.getEllipsisCount(lineCount - 1);
                if (ellipsisCount > 0) {
                    final float textSize = getTextSize();
                    setTextSize(TypedValue.COMPLEX_UNIT_PX,(textSize -1));
                    measure(widthMeasureSpec,heightMeasureSpec);
                }
            }
        }
    }
}
