package com.chanfinecloud.cfl.weidgt;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.adapter.AbstractSpinerAdapter;
import com.chanfinecloud.cfl.adapter.NormalSpinerAdapter;


import java.util.List;

public class SpinerPopWindow extends PopupWindow implements OnItemClickListener {

    private Context mContext;
    private ListView mListView;
    private NormalSpinerAdapter mAdapter;
    private AbstractSpinerAdapter.IOnItemSelectListener mItemSelectListener;

    private List<String> tList;

    public SpinerPopWindow(Context context) {
        super(context);
        mContext = context;
        init();
    }


    public void setItemListener(AbstractSpinerAdapter.IOnItemSelectListener listener) {
        mItemSelectListener = listener;
    }

    private void init() {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.spiner_window_layout, null);
        setContentView(view);
        setWidth(LayoutParams.WRAP_CONTENT);
        setHeight(LayoutParams.WRAP_CONTENT);

        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00);
        setBackgroundDrawable(dw);

        mListView = (ListView) view.findViewById(R.id.listview);

        mAdapter = new NormalSpinerAdapter(mContext);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
    }

    public void refreshData(List<String> list, int selIndex) {
        this.tList = list;
        if (list != null && selIndex != -1) {
            mAdapter.refreshData(list, selIndex);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {

        dismiss();
        if (mItemSelectListener != null) {
            mItemSelectListener.onItemClick(pos);
        }
    }
}
