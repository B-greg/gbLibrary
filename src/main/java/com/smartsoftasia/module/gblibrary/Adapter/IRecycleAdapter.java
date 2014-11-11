package com.smartsoftasia.module.gblibrary.Adapter;

import java.util.Collection;

/**
 * Created by gregoire on 11/11/14.
 */
public interface IRecycleAdapter<T> {

        public Object getItem(int position);

        public void appendItems(Collection<T> items);

        public void appendItem(T item);

        public void clear();

}
