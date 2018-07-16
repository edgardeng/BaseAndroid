package com.edgardeng.baseandroid.ui;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edgardeng.baseandroid.BaseActivity;
import com.edgardeng.baseandroid.R;
import com.edgardeng.util.ILog;

import java.util.HashMap;

import butterknife.OnClick;

public class UIHome extends BaseActivity {

    private GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.page_home);
        gridView = (GridView)findViewById(R.id.gridview);
        String listName[] = {
                "登陆页",
                "Retrofit",
                "视图属性",
                "动画"
        };

        final Class series[] = {
                UILogin.class,
                UIApi.class,
                UIViewProperty.class,
                UIAnimation.class

        };

        GridViwAdapter adapter = new GridViwAdapter(this,listName);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ILog.i("OnItemClickListener " + i);
                forward(series[i]);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @OnClick(R.id.button_login)
    void onLogin(){
        toast("onLogin");
    }


    class GridViwAdapter extends BaseAdapter {

        private String[] names;
        private Context mContext;
        LayoutInflater mInflater; //
        private int mLayoutResource; //

        public GridViwAdapter(Context context, String[] names ) {
            this.mContext = context;
            this.names = names;
            mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            if(convertView == null)
                convertView = mInflater.inflate(R.layout.item_grid_view, viewGroup, false);

            TextView tv =(TextView) convertView.findViewById(R.id.item_grid_text );
            tv.setText(names[position]);

            return convertView;
        }

    }

}
