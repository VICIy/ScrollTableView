package com.viciy.scrolltableview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.viciy.scrolltableview.R;

import static android.view.MotionEvent.ACTION_MOVE;

/**
 * Created by bai_qiang.yang
 * 2017/7/24 15:25
 * Mail: viciyforever@gmail.com
 */

public class ScrollTableView extends RelativeLayout {

    private int mLayoutRes;
    private RelativeLayout mHead;
    private View mHeadNameCodeContainer;
    private TableLineScrollView mHeadScrollTableView;
    private SingleLineTextView mHeadNameCode;
    private LinearLayout mHeadAttributeContainer;
    private Mode mode = Mode.noMove;

    private enum Mode {
        verticalMove, horizontalMove, noMove
    }

    public ScrollTableView(Context context) {
        this(context, null);
    }

    public ScrollTableView(Context context, AttributeSet attrs) {
        this(context, null, 0);
    }

    public ScrollTableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.ScrollTableView, defStyleAttr, 0);
        mLayoutRes = array.getResourceId(R.styleable.ScrollTableView_layout_res, R.layout.scroll_table_view);
        array.recycle();
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, mLayoutRes, this);
        //头部可滑动部分
        mHead = (RelativeLayout) findViewById(R.id.scroll_table_view_head);
        mHeadNameCodeContainer = mHead.findViewById(R.id.name_code_container);
        mHeadNameCode = (SingleLineTextView) mHead.findViewById(R.id.tv_name_code);
        mHeadScrollTableView = (TableLineScrollView) mHead.findViewById(R.id.line_scroll_view);
        mHeadAttributeContainer = (LinearLayout) mHead.findViewById(R.id.line_scroll_view_container);

        mHead.setFocusable(true);
        mHead.setClickable(true);
        mHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener(true));



    }

    class ListViewAndHeadViewTouchLinstener implements OnTouchListener {
        float downX, downY, moveX, moveY;
        boolean canChangeStatus = true;

        boolean isHead = false;

        public ListViewAndHeadViewTouchLinstener(boolean head) {
            isHead = head;
        }

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = event.getX();
                    downY = event.getY();
                    mode = Mode.noMove;
                    canChangeStatus = true;
                    mHeadScrollTableView.onTouchEvent(event);
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (canChangeStatus) {
                        requestDisallowInterceptTouchEvent(true);
                        moveX = event.getX();
                        moveY = event.getY();
                        float deltaX = Math.abs(moveX - downX);
                        float deltaY = Math.abs(moveY - downY);
                        if (deltaX + deltaY > 30) {
                            if (deltaX > deltaY) {
                                mode = Mode.horizontalMove;
                            } else {
                                mode = Mode.verticalMove;
                            }
                            canChangeStatus = false;
                        }
                    }
                    break;
                default:
                    break;

            }
            if (mode == Mode.horizontalMove) {
                mHeadScrollTableView.onTouchEvent(event);
                if (event.getAction() != ACTION_MOVE) {
                    return false;
                } else {
                    return true;
                }
            } else if (mode == Mode.verticalMove) {
                return false;
            } else {
                return false;
            }
        }
    }
}
