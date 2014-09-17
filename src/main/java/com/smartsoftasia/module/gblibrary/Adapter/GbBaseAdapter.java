package com.smartsoftasia.module.gblibrary.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by gregoire on 9/17/14.
 */
public abstract class GbBaseAdapter<T> extends BaseAdapter {

    protected List<T> items;
    private Context mContext;
    protected LayoutInflater mInflater;

    public GbBaseAdapter(List<T> items, Context mContext) {
        this.items = items;
        this.mContext = mContext;
        this.mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return view;
    }

    public Context getContext() {
        return mContext;
    }

}
