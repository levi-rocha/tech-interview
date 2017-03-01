package io.github.pino.androidsolution;

import android.view.LayoutInflater;
import android.view.View;

public interface ListItem {

    public int isListHeaderItem();
    public View getListItemView(LayoutInflater inflater, View convertView);

}
