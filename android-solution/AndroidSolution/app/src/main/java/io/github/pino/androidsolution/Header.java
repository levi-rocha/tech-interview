package io.github.pino.androidsolution;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by nadaaver on 2017-02-23.
 */

public class Header implements ListItem {

    private String title;

    public Header(String title) {
        this.title = title;
    }

    @Override
    public int isListHeaderItem() {
        return 1;
    }

    @Override
    public View getListItemView(LayoutInflater inflater, View convertView) {
        //TODO layouts
        View view;
        if (convertView == null) {
            view = inflater.inflate(R.layout.header, null);
        } else {
            view = convertView;
        }
        TextView txtHeaderTitle = (TextView) view.findViewById(R.id.txtHeaderTitle);
        txtHeaderTitle.setText(title);
        Typeface custom_font = Typeface.createFromAsset(inflater.getContext().getAssets(),  "fonts/Montserrat-SemiBold.otf");
        txtHeaderTitle.setTypeface(custom_font);
        return view;
    }
}
