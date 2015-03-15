package com.hcl.myui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangchuanliang on 2015/3/15.
 */
public class FlowLayout extends ViewGroup {
    private final String TAG = "FlowLayout";

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeheight = MeasureSpec.getMode(heightMeasureSpec);

        int width = 0;
        int height = 0;

        int lineWidth = 0;
        int lineHeight = 0;
        int count = getChildCount();

        for (int i = 0; i < count; i++) {
            View vChild = getChildAt(i);
            measureChild(vChild, widthMeasureSpec, heightMeasureSpec);//Ask one of the children of this view to measure itself, taking into account both the MeasureSpec requirements for this view and its padding
            MarginLayoutParams mlp = (MarginLayoutParams) vChild.getLayoutParams();
            int childWidth = vChild.getMeasuredWidth() + mlp.leftMargin + mlp.rightMargin;
            int childHeight = vChild.getMeasuredHeight() + mlp.topMargin + mlp.bottomMargin;
            if ((lineWidth + childWidth) > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                width = Math.max(width, lineWidth);
                lineWidth = childWidth;///�µ�һ�еĿ�
                height += lineHeight;//ֻ�еȵ������ˣ���֪���ղ����е����height
                lineHeight = childHeight;//����һ��,heightҪ����

            } else {//û�л���
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
            if (i == (count - 1)) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }

        }
        Log.e(TAG, "height = " + height);
        Log.e(TAG, "width = " + width);
            setMeasuredDimension(
                    modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingLeft() + getPaddingRight(),
                    modeheight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingBottom() + getPaddingTop()
            );
        }






    private List<List<View>> mAllViews = new ArrayList<List<View>>();//�洢ÿһ�е�ÿһ��view���Ա�layout
    private List<Integer> listHeight = new ArrayList<Integer>();//�洢ÿһ�еĸ�

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        listHeight.clear();
        int width = getWidth();//��ǰviewGroup�Ŀ��
        int lineWidth = 0;
        int lineHeight = 0;
        List<View> lineViews = new ArrayList<View>();
        int vCount = getChildCount();
        for (int i = 0; i < vCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams mlp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();//����ʹ��getWidth();
            int childHeight = child.getMeasuredHeight();
            if ((lineWidth + childWidth + mlp.leftMargin + mlp.rightMargin) > (width - getPaddingRight() - getPaddingLeft())) {
                    listHeight.add(lineHeight);//����������height���� list
                    mAllViews.add(lineViews);
                    lineViews=new ArrayList<View>();
                    lineWidth =0;
                    lineHeight = childHeight+mlp.topMargin+mlp.bottomMargin;

           }
                  lineViews.add(child);
                  lineWidth += childWidth+mlp.leftMargin+mlp.rightMargin;
                  lineHeight = Math.max(lineHeight,childHeight+mlp.bottomMargin+mlp.topMargin);
        }
                  mAllViews.add(lineViews);
                  listHeight.add(lineHeight);//�������һ��view
        int left = getPaddingLeft();
        int top = getPaddingTop();
        for (int i = 0;i < mAllViews.size();i++){
             int lHeight = listHeight.get(i);//��ȡ��ǰ�е����Height
              List<View> views = mAllViews.get(i);//��ǰ�е�����view
             for (int j = 0;j <views.size();j++){
              View childView = views.get(j);

              MarginLayoutParams lp=(MarginLayoutParams)childView.getLayoutParams();
              int lc =left + lp.leftMargin;
              int tc= top + lp.topMargin;
              int rc = lc + childView.getMeasuredWidth();
              int bc = tc + childView.getMeasuredHeight();
              if (childView.getVisibility() ==View.GONE){
                  continue;
              }
              childView.layout(lc,tc,rc,bc);
              Log.e(TAG,"��"+i+"->"+j+" l,t,r,b"+lc+" "+tc+" "+rc+" "+bc);
              left += childView.getMeasuredWidth()+lp.leftMargin+lp.rightMargin;
              }
             left = getPaddingLeft();
             top += lHeight;
        }



    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }
}
