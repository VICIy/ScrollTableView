package com.viciy.scrolltableview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * 本控件的观察者持有所有的 table line, 统一使所有行同步横向滑动
 */
public class TableLineScrollView extends HorizontalScrollView {

    private View left_arrow;
    private View right_arrow;
    private int rightEdge = -1;

    ScrollViewObserver mScrollViewObserver = new ScrollViewObserver();

    public TableLineScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public TableLineScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TableLineScrollView(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    public void setArrow(View left_arrow, View right_arrow){
        this.left_arrow = left_arrow;
        this.right_arrow = right_arrow;
        if(right_arrow != null){
            right_arrow.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mScrollViewObserver != null) {
            mScrollViewObserver.NotifyOnScrollChanged(l, t, oldl, oldt);
            if(right_arrow != null) {
                if (l != 0) {
                    right_arrow.setVisibility(View.GONE);
                } else {
                    right_arrow.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    /*
     * 订阅 本控件 的 滚动条变化事件
     * */
    public void AddOnScrollChangedListener(OnScrollChangedListener listener) {
        mScrollViewObserver.AddOnScrollChangedListener(listener);
    }

    /*
     * 取消 订阅 本控件 的 滚动条变化事件
     * */
    public void RemoveOnScrollChangedListener(OnScrollChangedListener listener) {
        mScrollViewObserver.RemoveOnScrollChangedListener(listener);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    /*
     * 当发生了滚动事件时
     */
    public interface OnScrollChangedListener {
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }

    /*
     * 观察者
     */
    public static class ScrollViewObserver {
        List<OnScrollChangedListener> mList;

        public ScrollViewObserver() {
            super();
            mList = new ArrayList<OnScrollChangedListener>();
        }

        public void AddOnScrollChangedListener(OnScrollChangedListener listener) {
            mList.add(listener);
        }

        public void RemoveOnScrollChangedListener(OnScrollChangedListener listener) {
            mList.remove(listener);
        }

        public void NotifyOnScrollChanged(int l, int t, int oldl, int oldt) {
            if (mList != null) {
                for (OnScrollChangedListener listener : mList) {
                    listener.onScrollChanged(l, t, oldl, oldt);
                }
            }
        }
    }
}
