package io.github.pino.androidsolution;

import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by nadaaver on 2017-02-23.
 */

public interface ListItem {

    public int isHeader();
    public View getListItemView(LayoutInflater inflater, View convertView);

}
