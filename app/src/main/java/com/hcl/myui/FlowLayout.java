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
               int sizeWidth =MeasureSpec.getSize(widthMeasureSpec);
               int modeWidth =MeasureSpec.getMode(widthMeasureSpec);
               int sizeHeight=MeasureSpec.getSize(heightMeasureSpec);
               int modeheight=MeasureSpec.getMode(heightMeasureSpec);

               int width = 0;
               int height =0;

               int lineWidth = 0;
               int lineHeight = 0;
               int count = getChildCount();
               for (int i=0;i < count;i++){
                   View vChild=getChildAt(i);
                   measureChild(vChild,widthMeasureSpec,heightMeasureSpec);//Ask one of the children of this view to measure itself, taking into account both the MeasureSpec requirements for this view and its padding
                   MarginLayoutParams mlp = (MarginLayoutParams) vChild.getLayoutParams();
                   int childWidth = vChild.getMeasuredWidth()+mlp.leftMargin+mlp.rightMargin;
                   int childHeight=vChild.getMeasuredHeight()+mlp.topMargin+mlp.bottomMargin;
                   if ((lineWidth + childWidth) > sizeWidth-getPaddingLeft()-getPaddingRight()){
                       width = Math.max(width,lineWidth);
                       lineWidth = childWidth;//新的一行的宽
                       height += lineHeight;//只有等到换行了，才知道刚才那行的最大height
                       lineHeight = childHeight;//换了一行,height要更新

                   }else {//没有换行
                       lineWidth += childWidth;
                       lineHeight = Math.max(lineHeight,childHeight);
                   }
                   if (i == (count - 1)){
                       width = Math.max(lineWidth,width);
                       height += lineHeight;
                   }
                   Log.e(TAG,"height = "+height);
                   Log.e(TAG,"width = "+width);
                   setMeasuredDimension(
                           modeWidth == MeasureSpec.EXACTLY ?sizeWidth:width+getPaddingLeft()+getPaddingRight(),
                           modeheight==MeasureSpec.EXACTLY ?sizeHeight:height+getPaddingBottom()+getPaddingTop()
                   );




               }



    }
    private List<List<View>> mAllViews = new ArrayList<List<View>>();//存储每一行的每一个view，以便layout
    private List<Integer> listHeight = new ArrayList<Integer>();//存储每一行的高
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
           mAllViews.clear();
           listHeight.clear();
           int width = getWidth();//当前viewGroup的宽度
           int lineWidth = 0;
           int lineHeight= 0;
           List<View> lineViews = new ArrayList<View>();
           int vCount =getChildCount();
           for (int i = 0;i < vCount;i++){
               View child = getChildAt(i);
               int childWidth = child.getMeasuredWidth();//不可使用getWidth();
               if ((lineWidth+childWidth) > )


           }

    }
}
