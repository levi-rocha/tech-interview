package io.github.pino.androidsolution;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by nadaaver on 2017-02-23.
 */

public class ArticleArrayAdapter extends ArrayAdapter<ListItem> {
    private LayoutInflater mInflater;

    public ArticleArrayAdapter(Context context, List<ListItem> objects) {
        super(context, 0, objects);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).isListHeaderItem();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getItem(position).getListItemView(mInflater, convertView);
    }
}
