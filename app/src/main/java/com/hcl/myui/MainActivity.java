package com.hcl.myui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    private String[] mVals = new String[]
            { "Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
                    "Android", "Weclome Hello", "Button Text", "TextView" };

    private FlowLayout mFlowLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFlowLayout =(FlowLayout)findViewById(R.id.id_flowlayout);
        initData();
    }

    private void initData() {
        for (int i =0;i < mVals.length;i++){
            LayoutInflater inflater=this.getLayoutInflater();
            TextView textView= (TextView) inflater.inflate(R.layout.tv
                    ,mFlowLayout,false);
            textView.setText(mVals[i]);
            mFlowLayout.addView(textView);

        }
    }


}
