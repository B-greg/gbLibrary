package com.smartsoftasia.module.gblibrary.Adapter;

import java.util.Collection;

/**
 * Created by gregoire on 11/11/14.
 */
public interface IRecycleAdapter {

        public Object getItem(int i);

        public void appendItems(Collection<Object> items);

        public void appendItem(Object item);

        public void clear();

}
