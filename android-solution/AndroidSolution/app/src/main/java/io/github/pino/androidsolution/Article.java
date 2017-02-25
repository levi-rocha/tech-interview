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

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

import static io.github.pino.androidsolution.ArticleListActivity.ARTICLE;

/**
 * Created by nadaaver on 2017-02-23.
 */

public class Article implements ListItem, Parcelable, Comparable<Article> {

    public static final int MAX_IMAGES = 7;

    private int[] imageResourceIds;
    private String title;
    private int categoryId;
    private String content;
    private boolean favorite;
    private String id;
    private String date;

    //temp
    public Article(String title, String content) {
        this.title = title;
        this.content = content;
        categoryId = 0;
        imageResourceIds = new int[0];
        favorite = false;
        id = UUID.randomUUID().toString();
        date = DateFormat.getDateTimeInstance().format(new Date());
    }

    public Article(int[] imageResourceIds, String title, int categoryId, String content, boolean favorite) {
        this.imageResourceIds = imageResourceIds;
        this.title = title;
        this.categoryId = categoryId;
        this.content = content;
        this.favorite = favorite;
        id = UUID.randomUUID().toString();
        date = DateFormat.getDateTimeInstance().format(new Date());
    }

    public Article(int[] imageResourceIds, String title, int categoryId, String content, boolean favorite, String date) {
        this.imageResourceIds = imageResourceIds;
        this.title = title;
        this.categoryId = categoryId;
        this.content = content;
        this.favorite = favorite;
        id = UUID.randomUUID().toString();
        this.date = date;
    }

    @Override
    public int isListHeaderItem() {
        return 0;
    }

    @Override
    public View getListItemView(final LayoutInflater inflater, View convertView) {
        final Article article = this;
        //TODO layouts
        View view;
        if (convertView == null) {
            view = (View) inflater.inflate(R.layout.article_list_item, null);
            Button btnOpen = (Button) view.findViewById(R.id.btnArticleListOpen);
            if (imageResourceIds != null && imageResourceIds.length > 0) {
                final LinearLayout mGallery = (LinearLayout) view.findViewById(R.id.small_gallery);
                final HorizontalScrollView mScroller = (HorizontalScrollView) view.findViewById(R.id.small_scroller);
                DisplayMetrics displayMetrics = new DisplayMetrics();
                ((Activity) inflater.getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                final int width = displayMetrics.widthPixels;
                for (int resource : imageResourceIds) {
                    addImage(inflater, view, mGallery, resource, width);
                }
                if (imageResourceIds.length > 1) {
                    ImageView btnLeft = (ImageView) view.findViewById(R.id.btnArticleListLeft);
                    ImageView btnRight = (ImageView) view.findViewById(R.id.btnArticleListRight);
                    btnLeft.setVisibility(View.VISIBLE);
                    btnRight.setVisibility(View.VISIBLE);
                    btnLeft.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ObjectAnimator animator = ObjectAnimator.ofInt(mScroller, "scrollX", mScroller.getScrollX() - width);
                            animator.setDuration(360);
                            animator.start();
                        }
                    });
                    btnRight.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ObjectAnimator animator = ObjectAnimator.ofInt(mScroller, "scrollX", mScroller.getScrollX() + width);
                            animator.setDuration(360);
                            animator.start();
                        }
                    });
                }
            }
            btnOpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(inflater.getContext(), ArticleActivity.class);
                    intent.putExtra(ARTICLE, article);
                    Activity parent = (Activity)inflater.getContext();
                    parent.startActivity(intent);
                    //parent.finish();
                }
            });
        } else {
            view = convertView;
        }
        TextView txtTitle = (TextView) view.findViewById(R.id.txtArticleListItemTitle);
        txtTitle.setText(this.title);
        TextView txtPreview = (TextView) view.findViewById(R.id.txtArticleListItemPreview);
        if (content.length() > 60) {
            txtPreview.setText(content.substring(0, 60) + "...");
        } else {
            txtPreview.setText(content);
        }

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
        int images = 0;
        if (imageResourceIds != null) {
             images = imageResourceIds.length;
        } else {
            imageResourceIds = new int[0];
        }
        dest.writeInt(images);
        dest.writeIntArray(imageResourceIds);
        dest.writeString(title);
        dest.writeInt(categoryId);
        dest.writeString(content);
        boolean[] favorite_data = {favorite};
        dest.writeBooleanArray(favorite_data);
        dest.writeString(id);
        dest.writeString(date);
    }

    private Article(Parcel in) {
        int images = in.readInt();
        imageResourceIds = new int[images];
        in.readIntArray(imageResourceIds);
        title = in.readString();
        categoryId = in.readInt();
        content = in.readString();
        boolean[] favorite_data = new boolean[1];
        in.readBooleanArray(favorite_data);
        favorite = favorite_data[0];
        id = in.readString();
        date = in.readString();
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

    @Override
    public int compareTo(Article o) {
        DateFormat format = DateFormat.getDateInstance();
        try {
            Date myDate = format.parse(date);
            Date theirDate = format.parse(o.getDate());
            if (myDate.after(theirDate)) {
                return 1;
            } else if (myDate.before(theirDate)){
                return -1;
            } else {
                return 0;
            }
        } catch (ParseException e) {
            return 0;
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public int[] getImageResourceIds() {
        return imageResourceIds;
    }

    public void setImageResourceIds(int[] imageResourceIds) {
        this.imageResourceIds = imageResourceIds;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
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

}
