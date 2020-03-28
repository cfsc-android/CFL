package com.chanfinecloud.cfl.ui.fragment.mainfrg;

import android.view.LayoutInflater;
import android.view.View;

import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.ui.base.BaseFragment;

import org.xutils.view.annotation.ContentView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *  Damien
 *  开锁
 */
public class KeyFragment extends BaseFragment {

    private Unbinder unbinder;

    @Override
    protected void initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_key, null);
        setContentView(view);
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    protected void initData() {

    }
}
