package io.github.pino.androidsolution;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by nadaaver on 2017-02-23.
 */

public class Article implements ListItem {

    private Drawable image;
    private String title;
    private Category category;
    private StringBuffer content;

    //temp
    public Article(String title, String content) {
        this.title = title;
        this.content = new StringBuffer(content);
    }

    public Article(Drawable image, String title, Category category, StringBuffer content) {
        this.image = image;
        this.title = title;
        this.category = category;
        this.content = content;
    }

    @Override
    public int isHeader() {
        return 0;
    }

    @Override
    public View getListItemView(LayoutInflater inflater, View convertView) {
        //TODO layouts
        View view;
        if (convertView == null) {
            view = (View) inflater.inflate(R.layout.article_list_item, null);
            final LinearLayout mGallery = (LinearLayout) view.findViewById(R.id.small_gallery);
            final HorizontalScrollView mScroller = (HorizontalScrollView) view.findViewById(R.id.small_scroller);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity) inflater.getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            final int width = displayMetrics.widthPixels;
            addImage(inflater, view, mGallery, R.drawable.pino1, width);
            addImage(inflater, view, mGallery, R.drawable.pino2, width);
            ImageView btnLeft = (ImageView) view.findViewById(R.id.btnArticleListLeft);
            btnLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ObjectAnimator animator = ObjectAnimator.ofInt(mScroller, "scrollX", mScroller.getScrollX()-width);
                    animator.setDuration(360);
                    animator.start();
                }
            });
            ImageView btnRight = (ImageView) view.findViewById(R.id.btnArticleListRight);
            btnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ObjectAnimator animator = ObjectAnimator.ofInt(mScroller, "scrollX", mScroller.getScrollX()+width);
                    animator.setDuration(360);
                    animator.start();
                }
            });
        } else {
            view = convertView;
        }
        TextView txtTitle = (TextView) view.findViewById(R.id.txtArticleListItemTitle);
        txtTitle.setText(this.title);
        TextView txtPreview = (TextView) view.findViewById(R.id.txtArticleListItemPreview);
        txtPreview.setText(content.toString());
        return view;
    }

    private void addImage(LayoutInflater inflater, View view, LinearLayout mGallery, int drawable, int width) {
        ImageView imageView = new ImageView(inflater.getContext());
        imageView.setLayoutParams(new LinearLayout.LayoutParams(width, 720));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageDrawable(ResourcesCompat.getDrawable(view.getResources(), drawable, null));
        mGallery.addView(imageView);
    }


}
