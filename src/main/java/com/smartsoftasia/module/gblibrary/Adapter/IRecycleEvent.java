package com.smartsoftasia.module.gblibrary.Adapter;

/**
 * Created by gregoire on 11/11/14.
 */
public interface IRecycleEvent{
    public interface OnRecycleClick {
        public void onRecycleClick(int position);
    }
    public interface OnLongRecycleClick{
        public void onLongRecycleClick(int position);
    }
}
