package io.github.pino.androidsolution;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static io.github.pino.androidsolution.MainActivity.ARTICLE;

/**
 * Created by nadaaver on 2017-02-23.
 */

public class Article implements ListItem, Parcelable {

    public static final int MAX_IMAGES = 7;

    private int[] imageResourceIds;
    private String title;
    private int categoryId;

    public int[] getImageResourceIds() {
        return imageResourceIds;
    }

    public void setImageResourceIds(int[] imageResourceIds) {
        this.imageResourceIds = imageResourceIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String content;

    //temp
    public Article(String title, String content) {
        this.title = title;
        this.content = content;
        categoryId = 0;
        imageResourceIds = new int[0];
    }

    public Article(int[] imageResourceIds, String title, int categoryId, String content) {
        this.imageResourceIds = imageResourceIds;
        this.title = title;
        this.categoryId = categoryId;
        this.content = content;
    }

    @Override
    public int isHeader() {
        return 0;
    }

    @Override
    public View getListItemView(final LayoutInflater inflater, View convertView) {
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
            Button btnOpen = (Button) view.findViewById(R.id.btnArticleListOpen);
            btnOpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(inflater.getContext(), ArticleActivity.class);
                    intent.putExtra(ARTICLE, new Article("I wanna be the very best", "Like no one ever was"));
                    inflater.getContext().startActivity(intent);
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        int images = imageResourceIds.length;
        dest.writeInt(images);
        dest.writeIntArray(imageResourceIds);
        dest.writeString(title);
        dest.writeInt(categoryId);
        dest.writeString(content);
    }

    private Article(Parcel in) {
        int images = in.readInt();
        imageResourceIds = new int[images];
        in.readIntArray(imageResourceIds);
        title = in.readString();
        categoryId = in.readInt();
        content = in.readString();
    }

    public static final Parcelable.Creator<Article> CREATOR
            = new Parcelable.Creator<Article>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
}
