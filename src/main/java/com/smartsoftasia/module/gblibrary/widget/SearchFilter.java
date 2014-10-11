package com.smartsoftasia.module.gblibrary.widget;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.widget.SearchView;

import java.util.List;

/**
 * Created by gregoire on 9/17/14.
 */
public class SearchFilter extends SearchView implements SearchView.OnQueryTextListener {

    private IsearchFilter listner;

    public interface IsearchFilter{
        public void onSearchTextChange(String query);
    }

    public SearchFilter(Context context) {
        super(context);
    }

    public SearchFilter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setupSearchView(MenuItem searchItem) {

        if (isAlwaysExpanded()) {
            setIconifiedByDefault(false);
        } else {
            searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
                    | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        }

//        SearchManager searchManager = (SearchManager) context.getSystemService(Context.SEARCH_SERVICE);
//        if (searchManager != null) {
//            List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();
//
//            SearchableInfo info = searchManager.getSearchableInfo(context.getComponentName());
//            for (SearchableInfo inf : searchables) {
//                if (inf.getSuggestAuthority() != null
//                        && inf.getSuggestAuthority().startsWith("applications")) {
//                    info = inf;
//                }
//            }
//            mSearchView.setSearchableInfo(info);
//        }

        setOnQueryTextListener(this);
    }

    public boolean onQueryTextChange(String newText) {
        return false;
    }



    public boolean onQueryTextSubmit(String query) {
        if(listner != null){
            listner.onSearchTextChange(query);
        }
        return false;
    }

    protected boolean isAlwaysExpanded() {
        return false;
    }

    public void setOnTextChange(IsearchFilter listner){
        this.listner = listner;
    }


}
