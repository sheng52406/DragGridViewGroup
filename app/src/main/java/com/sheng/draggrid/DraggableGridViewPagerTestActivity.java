package com.sheng.draggrid;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sheng.draggrid.view.DraggableGridViewPager;


public class DraggableGridViewPagerTestActivity extends Activity {

    private static final String TAG = "TestActivity";

    private DraggableGridViewPager mDraggableGridViewPager;
    private Button mAddButton;
    private Button mRemoveButton;
    private Button mScroolButton;

    private ArrayAdapter<String> mAdapter;

    private int mGridCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.draggable_grid_view_pager_test);
        mDraggableGridViewPager = (DraggableGridViewPager) findViewById(R.id.draggable_grid_view_pager);
        mAddButton = (Button) findViewById(R.id.add);
        mRemoveButton = (Button) findViewById(R.id.remove);
        mScroolButton = (Button) findViewById(R.id.scroll);

        mAdapter = new ArrayAdapter<String>(this, 0) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final String text = getItem(position);
                if (convertView == null) {
                    convertView = (TextView) getLayoutInflater().inflate(R.layout.draggable_grid_item, null);
                }
                ((TextView) convertView).setText(text);
                return convertView;
            }

        };
        mDraggableGridViewPager.setAdapter(mAdapter);
        mDraggableGridViewPager.setOnPageChangeListener(new DraggableGridViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG, "onPageScrolled position=" + position + ", positionOffset=" + positionOffset
                        + ", positionOffsetPixels=" + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected position=" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d(TAG, "onPageScrollStateChanged state=" + state);
            }
        });
        mDraggableGridViewPager.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showToast(((TextView) view).getText().toString());
            }
        });
        mDraggableGridViewPager.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showToast(((TextView) view).getText().toString() + " long clicked!!!");
                return true;
            }
        });
        mDraggableGridViewPager.setOnRearrangeListener(new DraggableGridViewPager.OnRearrangeListener() {
            @Override
            public void onRearrange(int oldIndex, int newIndex) {
                Log.i(TAG, "OnRearrangeListener.onRearrange from=" + oldIndex + ", to=" + newIndex);
                String item = mAdapter.getItem(oldIndex);
                mAdapter.setNotifyOnChange(false);
                mAdapter.remove(item);
                mAdapter.insert(item, newIndex);
                mAdapter.notifyDataSetChanged();
            }
        });

        mAddButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mGridCount++;
                mAdapter.add("Grid" + mGridCount);
            }
        });

        mRemoveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdapter.getCount() > 0) {
                    mAdapter.remove(mAdapter.getItem(mAdapter.getCount() - 1));
                }
            }
        });

        mScroolButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAdapter.getCount()>0){
                    mDraggableGridViewPager.setCurrentPage(6,true);
                }
            }
        });

        findViewById(R.id.getCurrentItem).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast(String.valueOf(mDraggableGridViewPager.getCurrentPage()));
            }
        });
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
