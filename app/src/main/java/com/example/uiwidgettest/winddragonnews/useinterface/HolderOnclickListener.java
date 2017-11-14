package com.example.uiwidgettest.winddragonnews.useinterface;

import android.view.View;

import com.example.uiwidgettest.winddragonnews.HandleData.Data;
import com.example.uiwidgettest.winddragonnews.HandleData.DbData;
import com.example.uiwidgettest.winddragonnews.adapter.RecyclerViewAdapter;

/**
 * Created by 40774 on 2017/11/7.
 */

public interface HolderOnclickListener  {
    void OnClickLikeListener(RecyclerViewAdapter.ViewHolder viewHolder, DbData data, int position);
    void OnClickShareListener(RecyclerViewAdapter.ViewHolder viewHolder);

}
